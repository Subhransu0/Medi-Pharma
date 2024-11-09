package com.example.pharmacyapplication.model


import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails():Serializable{
    var userUid : String ? = null
    var userName : String?= null
    var medicineNames: MutableList<String>?=null
    var medicineImages: MutableList<String>?=null
    var medicinePrices: MutableList<String>?=null
    var medicineQuantities: MutableList<Int>?=null
    var address : String ?= null
    var totalPrice : String ?= null
    var phoneNumber : String ?= null
    var orderAccepted : Boolean = false
    var paymentReceived : Boolean = false
    var itemPushKey : String ? = null
    var currentTime : Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        medicineItemName: ArrayList<String>,
        medicineItemPrice: ArrayList<String>,
        medicineItemImage: ArrayList<String>,
        medicineItemQuantities: ArrayList<Int>,
        address: String,
        totalAmount: String,
        phone: String,
        time: Long,
        itemPushKey: String?,
        b: Boolean,
        b1: Boolean
    ) : this(){
        this.userUid = userId
        this.userName = name
        this.medicineNames = medicineItemName
        this.medicinePrices = medicineItemPrice
        this.medicineImages = medicineItemImage
        this.medicineQuantities = medicineItemQuantities
        this.address = address
        this.totalPrice = totalAmount
        this.phoneNumber = phone
        this.currentTime= time
        this.itemPushKey = itemPushKey
        this.orderAccepted = orderAccepted
        this.paymentReceived = paymentReceived


    }


   fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

  fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}