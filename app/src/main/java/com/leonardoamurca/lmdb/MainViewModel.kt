package com.leonardoamurca.lmdb

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leonardoamurca.lmdb.navigation.HomeCoordinator
import com.leonardoamurca.lmdb.ui.HomeTabBarView
import com.leonardoamurca.lmdb.utils.triggerEvent

class MainViewModel(
    private val coordinator: HomeCoordinator,
    app: Application
) : AndroidViewModel(app) {

    private val _selectedPosition = MutableLiveData(HomeTabBarView.Items.HOME)
    val selectedPosition: LiveData<HomeTabBarView.Items> get() = _selectedPosition

    fun init(item: HomeTabBarView.Items) {
        _selectedPosition.triggerEvent(item)
        coordinator.start()
    }

    fun onTabSelect(item: HomeTabBarView.Items) {
        _selectedPosition.triggerEvent(item)

        when (item) {
            HomeTabBarView.Items.HOME -> coordinator.start()
            HomeTabBarView.Items.FAVORITES -> coordinator.showFavorites()
        }
    }
}