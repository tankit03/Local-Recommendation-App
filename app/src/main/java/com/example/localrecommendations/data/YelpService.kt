package com.example.localrecommendations.data

import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpService {

    //     --url https://api.yelp.com/v3/businesses/search?location=LOCATION&term=TERM&categories=&open_now=true&sort_by=best_match&limit=20
    //    Client ID
    //    IZl37-WWLNacXwYTvV1noA
    //    API Key
    //    vI2J-g8ElhZqM16uNzuxxHHHnbIPV5agHuMxoZ_SrwLGq5R8MweddX1cLC2nxf2v1wQa40L-JZhtxiZh7G08xMU5nkpRZUfz0IH3Nhj2ryADnorJ_ZvxbpoEPWH7ZXYx
    @GET("businesses/search")
    suspend fun loadSearch(
        @Query("location") location: String?, //Location. Default will be current location
        @Query("term") term: String, //Search term e.g. restaurant, deli, etc. gives everything if left out
        @Query("open_now") open: String? = "true", //open now. should be true (unless we want to search for closed places)
        @Query("sort_by") sort: String = "best_match",
        @Query("limit") limit: Int = 20 //limit to number of results.
        //@Query("offset") offset: Int = 0 //amount to offset. only need if we want more than 1 page available
        //could add category if we wanted to??
    ) : Response<YelpResult>

    @GET("businesses/search")
    suspend fun loadLonLatSearch(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("term") term: String, //Search term e.g. restaurant, deli, etc. gives everything if left out
        @Query("open_now") open: String? = "true", //open now. should be true (unless we want to search for closed places)
        @Query("sort_by") sort: String = "best_match",
        @Query("limit") limit: Int = 20 //limit to number of results.
        //@Query("offset") offset: Int = 0 //amount to offset. only need if we want more than 1 page available
        //could add category if we wanted to??
    ) : Response<YelpResult>

    //Client implementation info found here: https://proandroiddev.com/headers-in-retrofit-a8d71ede2f3e
    companion object {
        private const val BASE_URL = "https://api.yelp.com/v3/"

        fun create() : YelpService {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("Authorization", "Bearer vI2J-g8ElhZqM16uNzuxxHHHnbIPV5agHuMxoZ_SrwLGq5R8MweddX1cLC2nxf2v1wQa40L-JZhtxiZh7G08xMU5nkpRZUfz0IH3Nhj2ryADnorJ_ZvxbpoEPWH7ZXYx")
//                        builder.header("accept", "application/json")
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
            }.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(YelpService::class.java)
        }
    }
}