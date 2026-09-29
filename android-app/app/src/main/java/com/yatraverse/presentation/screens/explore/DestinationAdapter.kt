package com.yatraverse.presentation.screens.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yatraverse.R
import com.yatraverse.data.models.Destination

class DestinationAdapter(
    private var destinations: List<Destination>
) : RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {

    class DestinationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.ivDestinationImage)
        val name: TextView = view.findViewById(R.id.tvDestinationName)
        val location: TextView = view.findViewById(R.id.tvDestinationLocation)
        val description: TextView = view.findViewById(R.id.tvDestinationDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destination, parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = destinations[position]
        holder.name.text = destination.name
        holder.location.text = destination.location
        holder.description.text = destination.description
        Glide.with(holder.image.context)
            .load(destination.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.image)
    }

    override fun getItemCount(): Int = destinations.size

    fun updateData(newDestinations: List<Destination>) {
        destinations = newDestinations
        notifyDataSetChanged()
    }
}
