package com.yy.basearchitectureinkotlin.Util

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yy.baselibrary.util.Logger

/**
 * @author YY
 * @create 2022/8/22
 * @Describe
 **/
class NetworkStateManager private constructor() {

    companion object {
        val TAG = NetworkStateManager::class.java.simpleName
        private var INSTANCE: NetworkStateManager? = null
        private val activeNetworkStatusMLD = MutableLiveData<Boolean>()

        @get:Synchronized
        val instance: NetworkStateManager?
            get() {
                if (INSTANCE == null) {
                    Logger.d("getInstance() called: Creating new instance")
                    INSTANCE = NetworkStateManager()
                }
                return INSTANCE
            }
    }
    /**
     * Updates the active network status live-data
     */
    fun setNetworkConnectivityStatus(connectivityStatus: Boolean) {
        Logger.d(
            "setNetworkConnectivityStatus() called with: connectivityStatus = [$connectivityStatus]"
        )
        if (Looper.myLooper() == Looper.getMainLooper()) {
            activeNetworkStatusMLD.setValue(connectivityStatus)
        } else {
            activeNetworkStatusMLD.postValue(connectivityStatus)
        }
    }

    /**
     * Returns the current network status
     */
    fun getNetworkConnectivityStatus(): LiveData<Boolean> {
        Logger.d("getNetworkConnectivityStatus() called")
        return activeNetworkStatusMLD
    }
}