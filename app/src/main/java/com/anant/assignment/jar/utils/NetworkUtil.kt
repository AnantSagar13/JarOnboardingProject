package com.anant.assignment.jar.utils

object NetworkUtil {
    fun getNetworkErrorMessage(exception: Throwable): String {
        return when (exception) {
            is java.net.UnknownHostException -> "No internet connection available"
            is java.net.SocketTimeoutException -> "Connection timeout. Please try again"
            is java.net.ConnectException -> "Unable to connect to server"
            is javax.net.ssl.SSLException -> "Secure connection failed"
            else -> exception.message ?: "Network error occurred"
        }
    }
}