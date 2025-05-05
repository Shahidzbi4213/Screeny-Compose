package com.shahid.iqbal.screeny

object NativeKeyProvider {
    init {
        System.loadLibrary("nativeapikey")
    }

    external fun getApiKey(): String
}
