package com.example.pharmacyapplication.Fragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pharmacyapplication.MenuBootomSheetFragment

import com.example.pharmacyapplication.R
import com.example.pharmacyapplication.adapter.MenuAdapter
import com.example.pharmacyapplication.adapter.PopularAdapter
import com.example.pharmacyapplication.databinding.FragmentHomeBinding
import com.example.pharmacyapplication.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private  lateinit var database : FirebaseDatabase
    private  lateinit var menuItems :MutableList<MenuItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.viewAll.setOnClickListener{
            val bottomSheetDialog = MenuBootomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }

        retriveAndDisplayPopularItem()

        return binding.root

    }

    private fun retriveAndDisplayPopularItem() {
        database = FirebaseDatabase.getInstance()
        val mediRef:DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()
        mediRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(mediSnapshot in snapshot.children){
                    val menuItem = mediSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it) }
                }

                randomPopularItem()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun randomPopularItem() {
        val index = menuItems.indices.toList().shuffled()
        val numItemToShow = 6
        val subsetMenuItemItem = index.take(numItemToShow).map { menuItems[it] }

        setPopularItemsAdapter(subsetMenuItemItem)
    }

    private fun setPopularItemsAdapter(subsetMenuItemItem: List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenuItemItem,requireContext())
        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.medical_bannerr,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.super_sale,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner ,ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList , ScaleTypes.FIT)
        imageSlider.setItemClickListener(object :ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage ="Selected Image $position"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_SHORT).show()
            }
        })

    }


}