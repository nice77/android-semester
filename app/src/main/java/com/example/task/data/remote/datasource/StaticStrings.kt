package com.example.task.data.remote.datasource

import okhttp3.MediaType.Companion.toMediaType

object StaticStrings {
    val CONTENT_TYPE = "application/json".toMediaType()
    const val AUTH_HEADER = "Authorization: true"
    val IMAGE_CONTENT_TYPE = "image/png".toMediaType()

}