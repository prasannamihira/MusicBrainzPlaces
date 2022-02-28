package com.mobilepay.musicbrainzplaces.data.network

import com.mobilepay.musicbrainzplaces.data.model.PlacesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MusicBrainzService {

    @Headers("User-Agent: nuDev/1.0.0 (nuDev@example.com) )")
    @GET("/ws/2/place?fmt=json")
    fun getPlaces(
        @Query("query") searchQuery: String?, @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<PlacesResponse?>
}
