package com.leonardoamurca.lmdb.navigation

class RootCoordinator(private val homeCoordinator: HomeCoordinator) {
    fun start() {
        // TODO: In the future, add logic to start different coordinators
        homeCoordinator.start()
    }
}