package com.example.pharmacyapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapplication.adapter.RecentBuyAdapter
import com.example.pharmacyapplication.databinding.ActivityRecentOrderItemsBinding
import com.example.pharmacyapplication.model.OrderDetails

class RecentOrderItems : AppCompatActivity() {
    private val binding : ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allmedicineNames : ArrayList<String>
    private lateinit var allmedicineImages : ArrayList<String>
    private lateinit var allmedicinePrice : ArrayList<String>
    private lateinit var allmedicineQuantites : ArrayList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.bkbtnn.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recentOrderItems = intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetails ->
            if(orderDetails.isNotEmpty()){
                val recentOrderItem = orderDetails[0]

                allmedicineNames = recentOrderItem.medicineNames as ArrayList<String>
                allmedicineImages = recentOrderItem.medicineImages as ArrayList<String>
                allmedicinePrice = recentOrderItem.medicinePrices as ArrayList<String>
                allmedicineQuantites = recentOrderItem.medicineQuantities as ArrayList<Int>
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        val rv = binding.rv
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this,allmedicineNames,allmedicineImages,allmedicinePrice,allmedicineQuantites)
        rv.adapter = adapter

    }
}