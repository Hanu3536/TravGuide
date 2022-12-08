package com.capstone.travguide.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.travguide.TravisConstants.TRAVIS_BASE_URL
import com.capstone.travguide.databinding.LayoutLocationListItemBinding
import com.capstone.travguide.models.Location
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel


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
        holder.bind(locations[position], position + 1)
    }

    override fun getItemCount(): Int = locations.size


    inner class TravisLocationsViewHolder(private val item: LayoutLocationListItemBinding): RecyclerView.ViewHolder(item.root) {
        fun bind(location: Location, index: Int) {
            item.apply {
                isLocationImages.setImageList(
                    mutableListOf<SlideModel>().apply {
                        location.images?.split(", ")?.forEach {
                            add(SlideModel(imageUrl = "$TRAVIS_BASE_URL/images/$it", scaleType = ScaleTypes.FIT))
                        }
                    }.toList()
                )

                tvLocationTitle.text = "$index. ${location.place}"
                tvLocationDistance.text = "${location.distanceKm} Kms"
                tvLocationDescription.text = location.description
                tvExternalHyperlink.text = location.hyperlinks
                tvLocationDirections.text = location.mapDirections

                tvExternalHyperlink.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(location.hyperlinks)
                    context.startActivity(intent)
                }

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