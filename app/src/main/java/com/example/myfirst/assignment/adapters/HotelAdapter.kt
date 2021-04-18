package com.example.myfirst.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirst.assignment.R
import com.example.myfirst.assignment.adapters.HotelAdapter.HotelViewHolder
import com.example.myfirst.assignment.models.Hotel
import com.example.myfirst.assignment.ui.home.RoomActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class HotelAdapter(
    var context: Context,
    var hotelList: List<Hotel>
) : RecyclerView.Adapter<HotelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contex, parent, false)
        return HotelViewHolder(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val path = "http://10.0.2.2:3012/profile/"
        val hotel = hotelList[position]
        Picasso.get().load(path + hotel.roomImage).resize(120, 60).into(holder.imgProfile)
        holder.tvName.text = hotel.hotelName
        holder.tvAddress.text = hotel.address
        holder.tvBed.text = hotel.noOfBed
        holder.imgProfile.setOnClickListener {
            val intent = Intent(context, RoomActivity::class.java)
            intent.putExtra("Image", hotel.roomImage)
            intent.putExtra("Name", hotel.hotelName)
            intent.putExtra("Address", hotel.address)
            intent.putExtra("Desc", hotel.description)
            intent.putExtra("Id", hotel.id)
            intent.putExtra("BedNo", hotel.noOfBed)
            intent.putExtra("RoomNo", hotel.roomNo)
            intent.putExtra("Phone", hotel.phone)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    inner class HotelViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imgProfile: CircleImageView
        var tvName: TextView
        var tvAddress: TextView
        var tvBed: TextView

        init {
            imgProfile = itemView.findViewById(R.id.imgProfile)
            tvName = itemView.findViewById(R.id.tvName)
            tvAddress = itemView.findViewById(R.id.tvAddress)
            tvBed = itemView.findViewById(R.id.tvBed)
        }
    }

}