package com.rach.swipestore.domain.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    val isConnected: Flow<Boolean>

}