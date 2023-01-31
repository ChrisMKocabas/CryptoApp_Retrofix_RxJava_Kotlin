package com.example.cryptoapp_retrofix_rxjava_kotlin.service

import com.example.cryptoapp_retrofix_rxjava_kotlin.model.CryptoModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    // base URL is defined in main activity
    @GET("ticker/price")
    fun getData() : Observable<List<CryptoModel>>

    //fun getData() : Call<List<CryptoModel>>
}