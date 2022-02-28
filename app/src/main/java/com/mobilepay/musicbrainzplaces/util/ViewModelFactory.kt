package com.mobilepay.musicbrainzplaces.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobilepay.musicbrainzplaces.places.PlacesShowInMapViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlacesShowInMapViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return PlacesShowInMapViewModel() as T
        }
        throw IllegalArgumentException("UnknownViewModel")
    }
}
