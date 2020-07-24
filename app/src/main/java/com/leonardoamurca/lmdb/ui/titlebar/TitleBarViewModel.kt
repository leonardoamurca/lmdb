package com.leonardoamurca.lmdb.ui.titlebar

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TitleBarViewModel(app: Application) : AndroidViewModel(app) {

    private val _title = MutableLiveData("")
    val title: LiveData<String> get() = _title

    fun init(title: String?) {
        _title.value = title
    }

    fun onBackButtonClick() {
        Log.d("onBackButtonClick", "The button was clicked!")
    }
}