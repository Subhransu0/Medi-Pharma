package com.example.pharmacyapplication.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater



import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pharmacyapplication.DetailsActivity
import com.example.pharmacyapplication.databinding.MenuItemBinding
import com.example.pharmacyapplication.model.MenuItem


class MenuAdapter(private val menuItems: List<MenuItem>, private val requireContextt: Context) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size
    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailActivity(position)
                }


            }
        }

        private fun openDetailActivity(position: Int) {
            val menuItem = menuItems[position]
            val intent = Intent(requireContextt, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.medicineName)
                putExtra("MenuItemPrice", menuItem.medicinePrice)
                putExtra("MenuItemDescription", menuItem.medicineDescription)
                putExtra("MenuItemImage", menuItem.medicineImage)
                putExtra("MenuItemIngredients", menuItem.medicinengridient)


            }
            requireContextt.startActivity(intent)

        }


        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuMedicineNamePopular.text = menuItem.medicineName
                menupricePopular.text = menuItem.medicinePrice
               val Urii= Uri.parse(menuItem.medicineImage)
                Glide.with(requireContextt).load(Urii).into(menuImage)


            }
        }


    }



}


