package com.example.pharmacyapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.pharmacyapplication.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding : ActivityChooseLocationBinding by lazy{
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val locationList = arrayOf(
            "Bhubaneswar",
            "Delhi",
            "Mumbai",
            "Kolkata",
            "Chennai",
            "Bangalore",
            "Hyderabad",
            "Ahmedabad",
            "Pune",
            "Jaipur",
            "Lucknow",
            "Indore",
            "Bhopal",
            "Visakhapatnam",
            "Patna",
            "Vadodara",
            "Nagpur",
            "Kochi",
            "Coimbatore",
            "Guwahati",
            "Surat",
            "Chandigarh",
            "Vijayawada",
            "Thiruvananthapuram",
            "Agra",
            "Varanasi",
            "Amritsar",
            "Rajkot",
            "Jodhpur",
            "Madurai",
            "Mysore",
            "Nashik",
            "Faridabad",
            "Aurangabad",
            "Ranchi",
            "Jabalpur",
            "Kanpur",
            "Meerut",
            "Raipur",
            "Dehradun"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)



    }
}