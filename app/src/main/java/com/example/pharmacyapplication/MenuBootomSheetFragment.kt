package com.example.pharmacyapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapplication.adapter.MenuAdapter
import com.example.pharmacyapplication.databinding.FragmentMenuBootomSheetBinding
import com.example.pharmacyapplication.model.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBootomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBootomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBootomSheetBinding.inflate(inflater, container, false)
        binding.ButtonBack.setOnClickListener {
            dismiss()
        }
        retrieveMenuItems()


        return binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val mediREf: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        mediREf.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (mediSnapshot in snapshot.children) {
                    val menuItem = mediSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                Log.d("ITEM", "onDataChange: Data Received")
                setAdapter()
            }



            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    private fun setAdapter() {
        if (menuItems.isNotEmpty()) {
            val adapter = MenuAdapter(menuItems, requireContext())
            binding.menurecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.menurecyclerView.adapter = adapter
            Log.d("Items", "setAdapter: data set")
        }
        else{
            Log.d("ITEMS", "setAdapter: data Not Set")
        }
    }

    companion object {

    }

}