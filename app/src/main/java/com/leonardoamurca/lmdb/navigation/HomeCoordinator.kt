package com.leonardoamurca.lmdb.navigation

class HomeCoordinator(private val navigator: Navigator) {

    fun start() {
        navigator.showHomeScreen()
    }
}