package com.yy.basearchitectureinkotlin.Util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import com.yy.baselibrary.util.Logger
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

/**
 * @author YY
 * @create 2022/8/22
 * @Describe
 **/
class NetworkMonitoringUtil(context: Context) : ConnectivityManager.NetworkCallback() {
    val TAG = NetworkMonitoringUtil::class.java.simpleName
    private val mNetworkRequest: NetworkRequest = NetworkRequest.Builder()
        .removeCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
        .build()
    private val mConnectivityManager: ConnectivityManager
    private val mNetworkStateManager: NetworkStateManager

    // Constructor
    init {
        //需要先removeCapability，然後再addTransportType，才會監聽到VPN的狀態變化
        mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        mNetworkStateManager = NetworkStateManager.instance!!
    }

    companion object {
        val TAG: String = NetworkMonitoringUtil::class.java.simpleName

        fun getVPNNetworkInfo(context: Context): NetworkInfo? {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN)
        }

        fun isVPNNetworkConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork ?: return false
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            } else {
                val networks = connectivityManager.allNetworkInfo
                for (network in networks) {
                    if (network.type == ConnectivityManager.TYPE_VPN && network.isConnected) {
                        return true
                    }
                }
                return false
            }
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
                return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)!!
            } else {
                return connectivityManager.activeNetworkInfo?.isConnected ?: false
            }
        }

        fun getIpAddress(): String {
            val whatismyip = URL("http://checkip.amazonaws.com")
            var bufferedReader: BufferedReader? = null
            return try {
                bufferedReader = BufferedReader(
                    InputStreamReader(
                        whatismyip.openStream()
                    )
                )
                bufferedReader.readLine()
            }catch (e:Exception){
                bufferedReader?.close()
                ""
            }
            finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                bufferedReader?.close()
            }
        }
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Logger.d("onAvailable() called: Connected to network")
        mNetworkStateManager.setNetworkConnectivityStatus(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Logger.e("onLost() called: with: Lost network connection")
        mNetworkStateManager.setNetworkConnectivityStatus(false)
    }

    /**
     * Registers the Network-Request callback
     * (Note: Register only once to prevent duplicate callbacks)
     */
    fun registerNetworkCallbackEvents() {
        Logger.d("registerNetworkCallbackEvents() called")
        mConnectivityManager.registerNetworkCallback(mNetworkRequest, this)
    }

    /**
     * Check current Network state
     */
    fun checkNetworkState() {
        try {
            val networkInfo = mConnectivityManager.activeNetworkInfo
            mNetworkStateManager.setNetworkConnectivityStatus(
                networkInfo != null
                        && networkInfo.isConnected
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}