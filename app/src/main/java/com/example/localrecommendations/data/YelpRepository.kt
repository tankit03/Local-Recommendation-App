package com.example.localrecommendations.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
        return if (shouldFetch(location, term)) {
            try {
                val response = service.loadSearch(location, term)
                if (response.isSuccessful) {
                    cachedYelp = response.body()
                    timeStamp = timeSource.markNow()
                    currentLocation = location
                    currentTerm = term
                    Result.success(cachedYelp)
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                throw UnsupportedOperationException("Uh Oh. Something broke")
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