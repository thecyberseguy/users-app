package thecyberseguy.usersapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.InetSocketAddress
import java.net.Socket
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkChecker @Inject constructor(@ApplicationContext private val context: Context) {

    val isConnected: Boolean
        get() {
            var isConnected = false

            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
                getNetworkCapabilities(activeNetwork)?.run {
                    isConnected = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }

            if (isConnected) {
                val socket = Socket()

                try {
                    socket.connect(InetSocketAddress("www.google.com", 443), 6000)
                    isConnected = true
                } catch (ex: UnknownHostException) {
                    isConnected = false
                    ex.printStackTrace()
                } catch (ex: Exception) {
                    isConnected = false
                    ex.printStackTrace()
                } finally {
                    try {
                        socket.close()
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }

            return isConnected
        }

}