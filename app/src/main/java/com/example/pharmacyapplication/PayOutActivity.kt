package com.example.pharmacyapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pharmacyapplication.databinding.ActivityPayOutBinding
import com.example.pharmacyapplication.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var medicineItemName: ArrayList<String>
    private lateinit var medicineItemPrice: ArrayList<String>
    private lateinit var medicineItemImage: ArrayList<String>
    private lateinit var medicineItemDescription: ArrayList<String>
    private lateinit var medicineItemIngredient: ArrayList<String>
    private lateinit var medicineItemQuantities: ArrayList<Int>
    private lateinit var database: DatabaseReference
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference()
        setUserData()

        val intent = intent
        medicineItemName = intent.getStringArrayListExtra("MedicineItemName") as ArrayList<String>
        medicineItemPrice = intent.getStringArrayListExtra("MedicineItemPrice") as ArrayList<String>
        medicineItemImage = intent.getStringArrayListExtra("MedicineItemImage") as ArrayList<String>
        medicineItemDescription =
            intent.getStringArrayListExtra("MedicineItemDescription") as ArrayList<String>
        medicineItemIngredient =
            intent.getStringArrayListExtra("MedicineItemIngredient") as ArrayList<String>
        medicineItemQuantities =
            intent.getIntegerArrayListExtra("MedicineItemQuantities") as ArrayList<Int>

        totalAmount = calculateTotalAmount().toString() + "₹"
        binding.totalAmounttttt.isEnabled = false
        binding.totalAmounttttt.setText(totalAmount)
        binding.backButonnn.setOnClickListener {
            finish()
        }

        binding.placeMyOrderButtonnn.setOnClickListener {
            name = binding.nameeee.text.toString().trim()
            address = binding.addresssss.text.toString().trim()
            phone = binding.phoneeee.text.toString().trim()

            if (name.isBlank() && address.isBlank() && phone.isBlank()) {
                Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder()
            }

        }

    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid ?:""
        val time = System.currentTimeMillis()
        val itemPushKey = database.child("OrderDetails").push().key
        val orderDetails = OrderDetails(
            userId,
            name,
            medicineItemName,
            medicineItemPrice,
            medicineItemImage,
            medicineItemQuantities,
            address,
            totalAmount,
            phone,
            time,
            itemPushKey,
            false,
            false
        )
        val orderReference = database.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomsheetDialog = CongratsBottomSheet()
            bottomsheetDialog.show(supportFragmentManager, "Test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)
        }
            .addOnFailureListener {
                Toast.makeText(this, "failed to order", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails){
        database.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemFromCart() {
        val cartItemsReference = database.child("user").child(userId).child("CartItems")
        cartItemsReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until medicineItemPrice.size) {
            var price = medicineItemPrice[i]
            val lastChar = price.last()
            val priceIntValue = if (lastChar == '₹') {
                price.dropLast(1).toInt()
            } else {
                price.toInt()


            }
            var quantity = medicineItemQuantities[i]
            totalAmount += priceIntValue * quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userReference = database.child("user").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java) ?: ""
                        val address = snapshot.child("address").getValue(String::class.java) ?: ""
                        val phone = snapshot.child("phone").getValue(String::class.java) ?: ""
                        binding.apply {
                            nameeee.setText(name)
                            addresssss.setText(address)
                            phoneeee.setText(phone)
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

