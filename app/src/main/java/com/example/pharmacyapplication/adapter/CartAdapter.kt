package com.example.pharmacyapplication.adapter

import android.content.Context

import android.net.Uri

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.pharmacyapplication.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<String>,
    private val cartItemPrice: MutableList<String>,
    private var cartDescription: MutableList<String>,
    private val cartImage: MutableList<String>,
    private var cartQuantity: MutableList<Int>,
    private var cartIngredient: MutableList<String>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid ?:""
        val cartItemNumber = cartItems.size

        itemQuantities = IntArray(cartItemNumber) { 1 }
        cartItemRef = database.reference.child("user").child(userId).child("CartItems")
    }

    companion object {
        private var itemQuantities: IntArray = intArrayOf()
        private lateinit var cartItemRef: DatabaseReference
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size
    fun getupdatedItemQuantites(): MutableList<Int> {
        val itemQuantity = mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartMediName.text = cartItems[position]
                cartPriceMedicine.text = cartItemPrice[position]

                val uriString = cartImage[position]
                val urii = Uri.parse(uriString)
                Glide.with(context).load(urii).into(cartImageee)

                cartItemQuantity.text = quantity.toString()


                // Decrease quantity
                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }

                // Increase quantity
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }

                // Delete item from cart
                deleteButton.setOnClickListener {
                    val itemposition = adapterPosition
                    if (itemposition != RecyclerView.NO_POSITION) {
                        deleteItem(position)
                    }
                }
            }
        }

        // Decrease quantity function
        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                cartQuantity[position] = itemQuantities[position]
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        // Increase quantity function
        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                cartQuantity[position] = itemQuantities[position]
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        // Delete item from cart function
        private fun deleteItem(position: Int) {
            val positionREtrive = position
            getUniqKeyAtPosition(positionREtrive) { uniqueKey ->
                if (uniqueKey != null) {
                    removeItem(position, uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if (uniqueKey != null) {
                cartItemRef.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartImage.removeAt(position)
                    cartDescription.removeAt(position)
                    cartQuantity.removeAt(position)
                    cartItemPrice.removeAt(position)
                    cartIngredient.removeAt(position)
                    Toast.makeText(context, "Item delete", Toast.LENGTH_SHORT).show()
                    itemQuantities =
                        itemQuantities.filterIndexed { index, i -> index != position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, cartItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getUniqKeyAtPosition(positionREtrive: Int, onComplete: (String?) -> Unit) {
            cartItemRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqKey: String? = null
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == positionREtrive) {
                            uniqKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqKey)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }


}
