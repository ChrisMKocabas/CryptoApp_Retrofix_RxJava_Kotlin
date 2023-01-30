package com.example.cryptoapp_retrofix_rxjava_kotlin.service

import com.example.cryptoapp_retrofix_rxjava_kotlin.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    // base URL is defined in main activity
    @GET("ticker/price")
    fun getData() : Call<List<CryptoModel>>
}