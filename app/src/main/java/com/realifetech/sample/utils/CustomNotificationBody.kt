package com.realifetech.sample.utils

import com.google.gson.annotations.SerializedName

data class CustomNotificationBody(
        @SerializedName("u") val url: String)