package com.example.pharmacyapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapplication.adapter.NotificationAdapter
import com.example.pharmacyapplication.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList


class Notification_Bottom_Fragment : BottomSheetDialogFragment(){
    private lateinit var binding: FragmentNotificationBottomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater,container,false)
        val notification = listOf("Your order hasbenn cancelled sussessfully" , "Order has been taken by the driver" ," Congrats Your Order Placed")
        val notificationImage = listOf(R.drawable.sad,R.drawable.bike,R.drawable.congo)
        val adapter = NotificationAdapter(
            ArrayList(notification),
            ArrayList(notificationImage)

        )
        binding.notificationRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerview.adapter = adapter
        return binding.root
    }

    companion object {

    }
}