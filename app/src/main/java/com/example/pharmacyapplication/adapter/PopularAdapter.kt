package com.example.pharmacyapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapplication.DetailsActivity
import com.example.pharmacyapplication.databinding.PopularItemBinding

class PopularAdapter (private val items :List<String> , private val price:List<String>,private val images:List<Int>,private val requireContextt : Context) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val image = images[position]
        val price = price[position]
        holder.bind(item,price,image )
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContextt, DetailsActivity::class.java)
            intent.putExtra("MenuItemName" , item)
            intent.putExtra("MenuItemImage" , image)
            requireContextt.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class PopularViewHolder (private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.imageView6
        fun bind(item: String,price:String , image: Int) {
            binding.foodNamePopular.text = item
            binding.pricePopular.text = price
            imageView.setImageResource(image)


        }

    }
} 