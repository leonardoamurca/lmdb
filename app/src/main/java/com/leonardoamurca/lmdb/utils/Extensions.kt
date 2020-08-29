package com.leonardoamurca.lmdb.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.model.NetworkListResponse
import com.leonardoamurca.lmdb.network.NetworkState
import com.leonardoamurca.lmdb.network.NetworkState.Success
import com.leonardoamurca.lmdb.network.NetworkState.Error
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.ResourceForbidden
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.ResourceNotFound
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.InternalServerError
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.BadGateWay
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.ResourceRemoved
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.RemovedResourceFound
import com.leonardoamurca.lmdb.network.HttpStatus.MOVED_PERMANENTLY
import com.leonardoamurca.lmdb.network.HttpStatus.FOUND
import com.leonardoamurca.lmdb.network.HttpStatus.BAD_GATEWAY
import com.leonardoamurca.lmdb.network.HttpStatus.INTERNAL_SERVER_ERROR
import com.leonardoamurca.lmdb.network.HttpStatus.NOT_FOUND
import com.leonardoamurca.lmdb.network.HttpStatus.FORBIDDEN
import retrofit2.Response

fun Response<NetworkListResponse>.handleListStateResponse(): NetworkState<List<Movie>> {
    return if (this.isSuccessful) {
        Success(this.body()?.results!!)
    } else {
        when (this.code()) {
            FORBIDDEN.code -> ResourceForbidden(this.message())
            NOT_FOUND.code -> ResourceNotFound(this.message())
            INTERNAL_SERVER_ERROR.code -> InternalServerError(this.message())
            BAD_GATEWAY.code -> BadGateWay(this.message())
            MOVED_PERMANENTLY.code -> ResourceRemoved(this.message())
            FOUND.code -> RemovedResourceFound(this.message())
            else -> Error(this.message())
        }
    }
}

fun <T> MutableLiveData<T>.triggerEvent(value: T?) {
    this.value = value
}

fun <T> LiveData<T>.subscribe(owner: LifecycleOwner, body: T.() -> Unit) {
    this.removeObservers(owner)
    observe(owner, Observer { body(it) })
}