package com.example.pharmacyapplication.Fragement

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pharmacyapplication.R
import com.example.pharmacyapplication.RecentOrderItems
import com.example.pharmacyapplication.adapter.BuyAgainAdapter
import com.example.pharmacyapplication.databinding.FragmentHistoryBinding
import com.example.pharmacyapplication.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buysAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        // Inflate the layout for this fragment
        retriveBuyHistory()

        binding.recentBuyItemmmm.setOnClickListener {
            seeItemsRecentBuy()
        }
   //     sSetupRecyclerView()
        return binding.root
    }

    private fun seeItemsRecentBuy() {
        listOfOrderItem.firstOrNull()?.let { recentBuy ->
            val intent =Intent(requireContext(),RecentOrderItems::class.java)
            intent.putExtra("RecentBuyOrderItem", ArrayList(listOfOrderItem))

            startActivity(intent)

        }
    }

    private fun retriveBuyHistory() {
        binding.recentBuyItemmmm.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?:""
        val buyItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currentTime")
        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapShot in snapshot.children) {
                    val buyHistoryItem = buySnapShot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
                    setDataInRecentBuyItem()
                    setPreviousByyItemsRecyclerView()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataInRecentBuyItem() {
        binding.recentBuyItemmmm.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                buyAgainMedicineName.text = it.medicineNames?.firstOrNull() ?:""
                buyAgainMedicinePrice.text = it.medicinePrices?.firstOrNull() ?:""
                val image = it.medicineImages?.firstOrNull() ?:""
                Log.d("ImageURL", "Image URL: $image")
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainMedicineImage)
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {

                }
            }
        }
    }

    private fun setPreviousByyItemsRecyclerView() {
        val buyAgainMediName = mutableListOf<String>()
        val buyAgainMediPrice = mutableListOf<String>()
        val buyAgainMediImage = mutableListOf<String>()
        for (i in 1 until listOfOrderItem.size) {
            listOfOrderItem[i].medicineNames?.firstOrNull()?.let {
                buyAgainMediName.add(it)
                listOfOrderItem[i].medicinePrices?.firstOrNull()?.let {
                    buyAgainMediPrice.add(it)
                    listOfOrderItem[i].medicineImages?.firstOrNull()?.let {
                        buyAgainMediImage.add(it)
                    }
                }
                val rv = binding.BuyAgainRecyclerview
                rv.layoutManager = LinearLayoutManager(requireContext())
                buysAgainAdapter = BuyAgainAdapter(
                    buyAgainMediName,
                    buyAgainMediPrice,
                    buyAgainMediImage,
                    requireContext()
                )
                rv.adapter = buysAgainAdapter
            }

        }
    }
}