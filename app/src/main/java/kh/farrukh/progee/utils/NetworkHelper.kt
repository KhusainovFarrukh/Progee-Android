package kh.farrukh.progee.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 *Created by farrukh_kh on 3/16/22 10:17 AM
 *kh.farrukh.movix.utils
 **/
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

    val hasConnection: Boolean
        get() {
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as? ConnectivityManager ?: return false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork ?: return false
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

                result = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } else {
                @Suppress("DEPRECATION")
                connectivityManager.run {
                    activeNetworkInfo?.run {
                        result = type == ConnectivityManager.TYPE_WIFI ||
                                type == ConnectivityManager.TYPE_MOBILE ||
                                type == ConnectivityManager.TYPE_ETHERNET
                    }
                }
            }

            return result
        }
}