package com.example.cryptoapp_retrofix_rxjava_kotlin.model

import com.google.gson.annotations.SerializedName


data class CryptoModel(
    //@SerializedName("symbol")
    val symbol: String,     //in Kotlin serializedname can be omitted if variable names are the same as datasource
    //@SerializedName("price")
    val price: String)