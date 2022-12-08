package com.capstone.travguide.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.travguide.databinding.LayoutLocationListItemBinding
import com.capstone.travguide.models.Location


class TravisLocationsRecyclerAdapter(
    private val context: Context,
    private val locations: List<Location>
) : RecyclerView.Adapter<TravisLocationsRecyclerAdapter.TravisLocationsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravisLocationsViewHolder {
        return TravisLocationsViewHolder(
            LayoutLocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TravisLocationsViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int = locations.size


    inner class TravisLocationsViewHolder(private val item: LayoutLocationListItemBinding): RecyclerView.ViewHolder(item.root) {
        fun bind(location: Location) {
            item.apply {
                tvLocationTitle.text = "${location.id}. ${location.place}"
                tvLocationDescription.text = "Description: \n ${location.description}"
                tvLocationDirections.text = location.hyperlinks

                tvLocationDirections.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(location.hyperlinks))

                    intent.setClassName(
                        "com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"
                    )

                    context.startActivity(intent)
                }
            }
        }
    }
}