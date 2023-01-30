package com.example.cryptoapp_retrofix_rxjava_kotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp_retrofix_rxjava_kotlin.R
import com.example.cryptoapp_retrofix_rxjava_kotlin.adapter.RecyclerViewAdapter
import com.example.cryptoapp_retrofix_rxjava_kotlin.databinding.ActivityMainBinding
import com.example.cryptoapp_retrofix_rxjava_kotlin.model.CryptoModel
import com.example.cryptoapp_retrofix_rxjava_kotlin.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() , RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://www.binance.com/api/v3/"
    private var cryptoModels: ArrayList<CryptoModel>?  = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Recycler View
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()
    }

    private fun loadData() {
        //build a retrofit object
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON converter
            .build()

        //connect API to retrofit
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        //send the call asynchronously and receive response in callback function
        call.enqueue(object: Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful) {
                    //if body is not null
                    response.body()?.let { it ->
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let {model->
                            recyclerViewAdapter =
                                RecyclerViewAdapter(model, this@MainActivity)
                                binding.recyclerView.adapter = recyclerViewAdapter
                        } 
//                        for (model:CryptoModel in cryptoModels!!) {
//                            println(model.symbol)
//                            println(model.price)
//                        }
                    }
                }
                       }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                //in case of error, print error
                t.printStackTrace()
            }

        })
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.symbol}",Toast.LENGTH_LONG).show()
    }
}