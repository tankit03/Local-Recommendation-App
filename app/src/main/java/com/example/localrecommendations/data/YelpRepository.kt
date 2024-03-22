package com.example.localrecommendations.data

import android.util.Log
import com.example.localrecommendations.util.isLonLat
import com.example.localrecommendations.util.parseLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeSource

class YelpRepository (
    private val service: YelpService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
){
    //this is all stolen from Assignment 4 code :)
    private var currentLocation: String? = null
    private var currentTerm: String? = null
    private var cachedYelp: YelpResult? = null

    private val cachedMaxAge = 30.minutes
    private val timeSource = TimeSource.Monotonic
    private var timeStamp = timeSource.markNow()

    suspend fun LoadYelp(
        location: String?,
        term: String
    ) :Result<YelpResult?> {
        if (location == null) {
            val location = "Corvallis"
        }
        return if (shouldFetch(location, term)) {
            withContext(ioDispatcher) {
                try {
                    var response = service.loadSearch(location, term)
                    if (isLonLat(location!!)){
                        val lonlat = parseLocation(location)
                        if (lonlat != null) {
                            val (a, b) = lonlat
                            response = service.loadLonLatSearch(a,b, term)
                        }
                    }

                    if (response.isSuccessful) {
                        cachedYelp = response.body()
                        Log.d("Repository","response body: ${response.body().toString()}")
                        timeStamp = timeSource.markNow()
                        currentLocation = location
                        currentTerm = term
                        Result.success(cachedYelp)
                    } else {
                        Result.failure(Exception(response.errorBody()?.string()))
                    }
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        } else {
            Result.success(cachedYelp!!)
        }
    }

    private fun shouldFetch(location: String?, term: String?): Boolean =
        cachedYelp == null
                || location != currentLocation
                || term != currentTerm
                || (timeStamp + cachedMaxAge).hasPassedNow()
}