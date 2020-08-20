package com.leonardoamurca.lmdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.leonardoamurca.lmdb.navigation.HomeCoordinator

class MainViewModel(
    private val coordinator: HomeCoordinator,
    app: Application
) : AndroidViewModel(app) {

    fun init() {
        coordinator.start()
    }
}