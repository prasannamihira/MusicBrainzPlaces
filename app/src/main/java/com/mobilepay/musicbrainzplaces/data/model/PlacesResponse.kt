package com.mobilepay.musicbrainzplaces.data.model

import com.google.gson.annotations.SerializedName

data class PlacesResponse(val places: List<PlacesItem>, val offset: Int, val count: Int)

data class PlacesItem(
    val address: String,
    val name: String,
    @SerializedName("life-span")
    val lifeSpan: LifeSpan?,
    val coordinates: Coordinates,
    val id: String
)
data class Coordinates(val latitude: String, val longitude: String)

data class LifeSpan(val begin: String, val end: String)
