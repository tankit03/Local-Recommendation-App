package com.example.localrecommendations.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localrecommendations.data.YelpRepository
import com.example.localrecommendations.data.YelpResult
import com.example.localrecommendations.data.YelpService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = YelpRepository(YelpService.create())

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _yelp = MutableLiveData<YelpResult?>(null)

    val yelp: LiveData<YelpResult?> = _yelp

    /*
     * The current error for the most recent API query is stored in this private property.  This
     * error is exposed to the outside world in immutable form via the public `error` property
     * below.
     */
    private val _error = MutableLiveData<Throwable?>(null)

    /**
     * This property provides the error associated with the most recent API query, if there is
     * one.  If there was no error associated with the most recent API query, it will be null.
     */
    val error: LiveData<Throwable?> = _error

    //@Query("location") location: String?, //Location. Default will be current location
    //        @Query("term") term: String, //Search term e.g. restaurant, deli, etc. gives everything if left out
    //        @Query("open_now") open: String? = "true", //open now. should be true (unless we want to search for closed places)
    //        @Query("sort_by") sort: String = "best_match",
    //        @Query("limit") limit: Int = 20 //limit to number of results.
    fun loadYelp(location: String, term: String) {
        viewModelScope.launch {
            val result = repository.LoadYelp(location, term)
            _error.value = result.exceptionOrNull()
            _yelp.value = result.getOrNull()
        }
    }
}