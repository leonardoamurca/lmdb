package com.leonardoamurca.lmdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.leonardoamurca.lmdb.navigation.RootCoordinator

class MainViewModel(
    private val coordinator: RootCoordinator,
    app: Application
) : AndroidViewModel(app) {

    fun init() {
        coordinator.start()
    }
}