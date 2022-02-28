package com.mobilepay.musicbrainzplaces.places

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mobilepay.musicbrainzplaces.MusicPlacesActivity
import com.mobilepay.musicbrainzplaces.R
import com.mobilepay.musicbrainzplaces.base.BaseViewModel
import com.mobilepay.musicbrainzplaces.data.model.PlacesItem
import com.mobilepay.musicbrainzplaces.data.network.MusicBrainzService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class PlacesShowInMapViewModel : BaseViewModel() {

    @Inject
    lateinit var musicBrainzApi: MusicBrainzService

    private lateinit var subscription: Disposable
    private val errorMessage: MutableLiveData<Int> = MutableLiveData()
    var searchText: MutableLiveData<String> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val placesListTotal = ArrayList<PlacesItem>()
    var totalPlacesCount: Int? = null

    init {
        MusicPlacesActivity.clearMap()

        fetchPlaces(searchText.value)
    }

    fun fetchPlaces(searchQuery: String?, offset: Int = 0) {
        subscription = Observable.fromCallable { }
            .concatMap {
                musicBrainzApi.getPlaces(searchQuery, PER_PAGE_ITEMS_LIMIT, offset)
                    .concatMap { apiPlacesList ->

                        totalPlacesCount = apiPlacesList.count

                        Observable.just(apiPlacesList)
                    }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePlacesStart() }
            .doOnTerminate { onRetrievePlacesFinish() }
            .subscribe(
                { result -> onRetrievePlacesSuccess(result.places, totalPlacesCount) },
                { onRetrievePlacesError() }
            )
    }

    private fun onRetrievePlacesStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePlacesFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePlacesSuccess(places: List<PlacesItem>, totalCount: Int?) {

        if (totalCount != null && totalCount > PER_PAGE_ITEMS_LIMIT) {

            val pagesCount: Int = totalCount / PER_PAGE_ITEMS_LIMIT

            for (placeIndex in 1..pagesCount) {
                placesListTotal.addAll(places)
                fetchPlaces(searchText.value, PER_PAGE_ITEMS_LIMIT * placeIndex)
            }
        } else {
            placesListTotal.addAll(places)
        }

        if (placesListTotal.isNotEmpty()) {
            addPlacesToTheMap(placesListTotal)
        }
    }

    private fun addPlacesToTheMap(placesListTotal: ArrayList<PlacesItem>) {
        try {
            // this filter statement comment due to all the records has null value
            // Display places opened 1990 or later
            /*var placesFilteredList =
                placesListTotal.filter { it.lifeSpan?.begin?.toInt()!! >= YEAR_FROM }*/

            placesListTotal.forEach {
                val placeName = it.name
                val coordinates = it.coordinates
                if (coordinates != null) {
                    val latitude: Double = java.lang.Double.valueOf(coordinates.latitude)
                    val longitude: Double = java.lang.Double.valueOf(coordinates.longitude)
                    val latLng = LatLng(latitude, longitude)

                    if (latLng != null) {
                        MusicPlacesActivity.mapObject?.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(placeName)
                        )
                    }
                }
            }
        } catch (ex: Exception) {
            Timber.e("Error in process response data")
        }
    }

    private fun onRetrievePlacesError() {
        errorMessage.value = R.string.map_loading_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    companion object {
        const val PER_PAGE_ITEMS_LIMIT = 20
        private const val YEAR_FROM = 1990
    }
}
