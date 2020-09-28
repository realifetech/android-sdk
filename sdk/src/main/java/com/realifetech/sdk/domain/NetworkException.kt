package com.realifetech.sdk.domain

class NetworkException(val httpCode: Int, message: String) : Exception(message)