package com.example.pharmacyapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pharmacyapplication.databinding.BuyAgainItemBinding

class BuyAgainAdapter(
    private val buyAgainMedicineName: MutableList<String>,
    private val buyAgainMediPrice: MutableList<String>,
    private val buyAgainMediImage: MutableList<String>,
    private var requireContextt: Context
) :
    RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {
    override fun onBindViewHolder(holder: BuyAgainAdapter.BuyAgainViewHolder, position: Int) {
        holder.bind(
            buyAgainMedicineName[position],
            buyAgainMediPrice[position],
            buyAgainMediImage[position]
        )

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuyAgainAdapter.BuyAgainViewHolder {
        val binding =
            BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }


    override fun getItemCount(): Int = buyAgainMedicineName.size
   inner class BuyAgainViewHolder(private val binding: BuyAgainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mediName: String, mediPrice: String, mediImage: String) {
            binding.buyAgainMedicineName.text = mediName
            binding.buyAgainMedicinePrice.text = mediPrice
            val uriStringg = mediImage
            val uri = Uri.parse(uriStringg)
            Glide.with(requireContextt).load(uri).into(binding.buyAgainMedicineImage)
        }

    }
}