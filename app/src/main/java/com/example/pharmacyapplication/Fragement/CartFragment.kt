package com.example.pharmacyapplication.Fragement

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapplication.CongratsBottomSheet
import com.example.pharmacyapplication.PayOutActivity
import com.example.pharmacyapplication.R
import com.example.pharmacyapplication.adapter.CartAdapter
import com.example.pharmacyapplication.databinding.FragmentCartBinding
import com.example.pharmacyapplication.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var medicineNames: MutableList<String>
    private lateinit var medicineprices: MutableList<String>
    private lateinit var medicineDescriptions: MutableList<String>
    private lateinit var medicineImageUri: MutableList<String>
    private lateinit var medicineIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        retriveCartItems()

        binding.proccedButton.setOnClickListener {
            getOrderItemDetails()

        }


        return binding.root
    }

    private fun getOrderItemDetails() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")
        val medicineName = mutableListOf<String>()
        val medicinePrice = mutableListOf<String>()
        val medicineImage = mutableListOf<String>()
        val medicineDescription = mutableListOf<String>()
        val medicineIngredient = mutableListOf<String>()
        val medicineQuantities = cartAdapter.getupdatedItemQuantites()
        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (medicineSnapShot in snapshot.children) {
                    val orderItems = medicineSnapShot.getValue(CartItems::class.java)
                    orderItems?.medicineName?.let { medicineName.add(it) }
                    orderItems?.medicinePrice?.let { medicinePrice.add(it) }
                    orderItems?.medicineDescription?.let { medicineDescription.add(it) }
                    orderItems?.medicineImage?.let { medicineImage.add(it) }
                    orderItems?.medicineIngridient?.let { medicineIngredient.add(it) }
                }
                orderNow(
                    medicineName,
                    medicinePrice,
                    medicineDescription,
                    medicineImage,
                    medicineIngredient,
                    medicineQuantities
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Order failed please try Again later !!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun orderNow(
        medicineName: MutableList<String>,
        medicinePrice: MutableList<String>,
        medicineDescription: MutableList<String>,
        medicineImage: MutableList<String>,
        medicineIngredient: MutableList<String>,
        medicineQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("MedicineItemName", medicineName as ArrayList<String>)
            intent.putExtra("MedicineItemPrice", medicinePrice as ArrayList<String>)
            intent.putExtra("MedicineItemDescription", medicineDescription as ArrayList<String>)
            intent.putExtra("MedicineItemImage", medicineImage as ArrayList<String>)
            intent.putExtra("MedicineItemIngredient", medicineIngredient as ArrayList<String>)
            intent.putExtra("MedicineItemQuantities", medicineQuantities as ArrayList<Int>)

            startActivity(intent)
        }

    }

    private fun retriveCartItems() {
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val medicineReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        medicineNames = mutableListOf()
        medicineprices = mutableListOf()
        medicineDescriptions = mutableListOf()
        medicineImageUri = mutableListOf()
        quantity = mutableListOf()
        medicineIngredients = mutableListOf()


        medicineReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (mediSnapshot in snapshot.children) {
                    val cartItems = mediSnapshot.getValue(CartItems::class.java)

                    cartItems?.medicineName?.let { medicineNames.add(it) }
                    cartItems?.medicinePrice?.let { medicineprices.add(it) }
                    cartItems?.medicineDescription?.let { medicineDescriptions.add(it) }
                    cartItems?.medicineImage?.let { medicineImageUri.add(it) }
                    cartItems?.medicineQuanntity?.let { quantity.add(it) }
                    cartItems?.medicineIngridient?.let { medicineIngredients.add(it) }
                }

                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter = CartAdapter(
                    requireContext(),
                    medicineNames,
                    medicineprices,
                    medicineDescriptions,
                    medicineImageUri,
                    quantity,
                    medicineIngredients
                )
                binding.cardRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cardRecyclerView.adapter = cartAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not Fetch", Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {

    }
}
