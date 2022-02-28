package com.mobilepay.musicbrainzplaces

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.mobilepay.musicbrainzplaces.databinding.ActivityMusicPlacesBinding
import com.mobilepay.musicbrainzplaces.places.PlacesShowInMapViewModel
import com.mobilepay.musicbrainzplaces.util.ViewModelFactory

class MusicPlacesActivity : AppCompatActivity(), OnMapReadyCallback, OnQueryTextListener {

    private lateinit var binding: ActivityMusicPlacesBinding

    private lateinit var viewModel: PlacesShowInMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_places)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory())
                .get(PlacesShowInMapViewModel::class.java)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.main_map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

        binding.searchView.setOnQueryTextListener(this)

        binding.viewModel = viewModel
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapObject = googleMap
    }

    companion object {
        var mapObject: GoogleMap? = null

        fun clearMap() {
            mapObject?.clear()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        clearMap()
        viewModel.placesListTotal.clear()

        viewModel.searchText.postValue(query)
        viewModel.fetchPlaces(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}
