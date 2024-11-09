package com.example.pharmacyapplication

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.pharmacyapplication.databinding.ActivityDetailsBinding
import com.example.pharmacyapplication.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding

    private var medicineName :String ?= null
    private var medicineImage :String ?= null
    private var medicineDescription :String ?= null
    private var medicineIngredient :String ?= null
    private var medicinePrice :String ?= null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        medicineName = intent.getStringExtra("MenuItemName")
        medicineDescription = intent.getStringExtra("MenuItemDescription")
        medicineIngredient = intent.getStringExtra("MenuItemIngredients")
        medicinePrice = intent.getStringExtra("MenuItemPrice")

        medicineImage = intent.getStringExtra("MenuItemImage")

        with(binding){
            detailMedicineName.text = medicineName
            detailMedicineDescription.text = medicineDescription
            detailMedicineIngridient.text = medicineIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(medicineImage)).into(detailMedicineImage)

        }



        binding.bkbtn.setOnClickListener {
            finish()
         }
        binding.addtooCartt.setOnClickListener{
            addItemToCart()
        }

    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""
        val cartItem = CartItems(medicineName.toString(),medicinePrice.toString(),medicineDescription.toString(),medicineImage.toString(),1)

      database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
          Toast.makeText(this, "Item added to Cart", Toast.LENGTH_SHORT).show()
      }.addOnFailureListener{
          Toast.makeText(this, "Item added to Cart Failed", Toast.LENGTH_SHORT).show()
      }
    }
}