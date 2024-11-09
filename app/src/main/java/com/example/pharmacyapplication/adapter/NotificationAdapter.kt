package com.example.pharmacyapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapplication.databinding.NotificationItemBinding

class NotificationAdapter(private var notification : ArrayList<String> , private var NotificationImage : ArrayList<Int>) : RecyclerView.Adapter<NotificationAdapter.NotiFicationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.NotiFicationViewHolder {
       val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  NotiFicationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NotificationAdapter.NotiFicationViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = notification.size
  inner  class NotiFicationViewHolder(private val binding: NotificationItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
             binding.apply {
                 notificationtextView.text = notification[position]
                 notificationimageView.setImageResource(NotificationImage[position])

             }
        }

    }
}