package com.mobilepay.musicbrainzplaces.base

import androidx.lifecycle.ViewModel
import com.mobilepay.musicbrainzplaces.dagger.DaggerViewModelInjector
import com.mobilepay.musicbrainzplaces.dagger.ViewModelInjector
import com.mobilepay.musicbrainzplaces.dagger.model.NetworkModule
import com.mobilepay.musicbrainzplaces.places.PlacesShowInMapViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModel(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is PlacesShowInMapViewModel -> injector.inject(this)
        }
    }
}
