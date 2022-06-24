package com.realifetech.sdk.core.data.model.exceptions

class NetworkException(val httpCode: Int, message: String) : Exception(message)