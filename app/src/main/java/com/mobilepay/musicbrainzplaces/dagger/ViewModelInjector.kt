package com.mobilepay.musicbrainzplaces.dagger

import com.mobilepay.musicbrainzplaces.dagger.model.NetworkModule
import com.mobilepay.musicbrainzplaces.places.PlacesShowInMapViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    @Component.Builder
    interface Builder {

        fun build(): ViewModelInjector

        fun networkModel(networkModel: NetworkModule): Builder
    }

    fun inject(placesShowInMapViewModel: PlacesShowInMapViewModel)

}
