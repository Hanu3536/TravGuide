package com.capstone.travguide.models

import com.google.gson.annotations.SerializedName


data class Location(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("place")
    var place: String? = null,

    @SerializedName("latitude")
    var latitude: String? = null,

    @SerializedName("longitude")
    var longitude: String? = null,

    @SerializedName("images")
    var images: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("mapdirections")
    var mapDirections: String? = null,

    @SerializedName("hyperlinks")
    var hyperlinks: String? = null,

    @SerializedName("distancekm")
    var distanceKm: String? = null

)