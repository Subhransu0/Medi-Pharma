package com.example.pharmacyapplication.Fragement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapplication.adapter.MenuAdapter
import com.example.pharmacyapplication.databinding.FragmentSearchBinding
import com.example.pharmacyapplication.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItems = mutableListOf<MenuItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        retriveMenuItem()

        setupSearchView()
        return binding.root
    }

    private fun retriveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val mediReference: DatabaseReference = database.reference.child("menu")
        mediReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (mediSnapshot in snapshot.children) {
                    val menuItem = mediSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        originalMenuItems.add(it)
                    }
                }
                showAllmenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showAllmenu() {
        val filteredMenuItem = ArrayList(originalMenuItems)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<MenuItem>) {
        adapter = MenuAdapter(filteredMenuItem, requireContext())
        binding.menurecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menurecyclerView.adapter = adapter
    }


    private fun setupSearchView() {
        // Ensure searchView is initialized correctly
        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    filterMenuItems(query)
                }
                return true // Return true to indicate the query was handled
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterMenuItems(newText)
                }
                return true // Return true to indicate the text change was handled
            }
        })
    }

    private fun filterMenuItems(query: String) {
       val filteredMenuItems = originalMenuItems.filter {
           it.medicineName?.contains(query, ignoreCase = true) == true
       }



       setAdapter(filteredMenuItems)
    }

   
}
