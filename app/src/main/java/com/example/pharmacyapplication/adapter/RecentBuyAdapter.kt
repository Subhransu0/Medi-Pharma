package com.example.pharmacyapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pharmacyapplication.databinding.RecentBuyItemBinding

class RecentBuyAdapter(
    private var context: Context,
    private var medicineNameList: ArrayList<String>,
    private var medicineImageList: ArrayList<String>,
    private var medicinePriceList: ArrayList<String>,
    private var medicineQuantityList: ArrayList<Int>,
) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding =
            RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = medicineNameList.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(position)
    }

 inner  class RecentViewHolder(private val binding: RecentBuyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
           binding.apply {
               mediName.text = medicineNameList[position]
               mediPrice.text = medicinePriceList[position]
               mediQuantity.text = medicineQuantityList[position].toString()
               val uriString = medicineImageList[position]
               val uri = Uri.parse(uriString)
               Glide.with(context).load(uri).into(mediImage)
           }
        }

    }
}