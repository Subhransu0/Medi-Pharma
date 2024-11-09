package com.example.pharmacyapplication.Fragement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pharmacyapplication.R
import com.example.pharmacyapplication.databinding.FragmentProfileBinding
import com.example.pharmacyapplication.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        setUserData()
        binding.savechangesBtn.setOnClickListener {
            val name = binding.nameeeeeeeeeeeee.text.toString()
            val email = binding.emailllllllllllll.text.toString()
            val addresss = binding.addresssssss.text.toString()
            val phone = binding.phoneeeeee.text.toString()


            updateUserData(name, email, addresss, phone)
        }
        return binding.root
    }

    private fun updateUserData(name: String, email: String, addresss: String, phone: String) {
        val userId = auth.currentUser?.uid
        if(userId != null){
            val userReference = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "address" to addresss,
                "email" to email,
                "phone" to phone
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Update successfully", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Profile Update failed", Toast.LENGTH_SHORT).show()
                }

        }
    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userREference = database.getReference("user").child(userId)
            userREference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null) {
                            binding.nameeeeeeeeeeeee.setText(userProfile.name)
                            binding.addresssssss.setText(userProfile.address)
                            binding.emailllllllllllll.setText(userProfile.email)
                            binding.phoneeeeee.setText(userProfile.phone)

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }


}