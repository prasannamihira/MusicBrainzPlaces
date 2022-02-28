package com.mobilepay.musicbrainzplaces.util.adapter

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.mobilepay.musicbrainzplaces.util.extension.getParentActivity

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(
            parentActivity,
            androidx.lifecycle.Observer { value -> view.visibility = value ?: View.VISIBLE })
    }
}
