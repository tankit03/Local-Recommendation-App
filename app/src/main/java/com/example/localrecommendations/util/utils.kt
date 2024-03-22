package com.example.localrecommendations.util

import java.lang.NumberFormatException

fun isLonLat(input : String): Boolean {
    return input.startsWith("LONLAT:", ignoreCase = false)
}

fun parseLocation(input: String): Pair<Double, Double>? {
    if(!isLonLat(input)) return null
    val parts = input.substringAfter("LONLAT:", "").split(",")
    if (parts.size != 2) return null

    try {
        val longitude = parts[0].toDouble()
        val latitude = parts[1].toDouble()

        return longitude to latitude
    } catch (e: NumberFormatException) {
        return null
    }
}