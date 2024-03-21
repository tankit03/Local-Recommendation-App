package com.example.localrecommendations.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class YelpResult (
    @Json(name = "businesses") val result: List<Businesses>
)

//generic Json Adapters. Could add more fields if we want them like distance, menu link, transactions available, etc.
@JsonClass(generateAdapter = true)
class Businesses (
    val id: String, //unique business id. probably don't need
    val alias: String, //a bit more detailed than the name e.g. castor-kitchen-and-bar-corvallis
    val name: String, //restaurant name e.g. Castor Kitchen & Bar
    val imageURL: String, //image URL. Maybe add an image to the individual restaurant card??
    val url: String, //website URL. Would be good to link somewhere
    val categories: List<Category>, //the categories for the restaurant e.g. new american
    val rating: Float, //restaurant rating e.g. 4.5
    val phone: String, //restaurant phone number
    val location: Location
)

@JsonClass(generateAdapter = true)
class Category (
    val alias: String, //category alias e.g. newamerican
    val title: String //display title e.g New American
)

@JsonClass(generateAdapter = true)
class Location (
    val address1: String, //addr line 1
    val address2: String?, //addr line 2
    val address3: String?, //addr line 3
    val city: String, //city
    @Json(name = "zip_code") val zip: String, //zip
    val country: String, //country
    val state: String, //state
    @Json(name = "display_address") val addr: List<String> //full address. Each string is a line of the address
)
