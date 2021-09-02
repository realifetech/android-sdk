package com.realifetech.sdk.core.domain

class NetworkException(val httpCode: Int, message: String) : Exception(message)