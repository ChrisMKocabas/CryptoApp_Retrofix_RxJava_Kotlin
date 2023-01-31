package com.example.cryptoapp_retrofix_rxjava_kotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp_retrofix_rxjava_kotlin.R
import com.example.cryptoapp_retrofix_rxjava_kotlin.adapter.RecyclerViewAdapter
import com.example.cryptoapp_retrofix_rxjava_kotlin.databinding.ActivityMainBinding
import com.example.cryptoapp_retrofix_rxjava_kotlin.model.CryptoModel
import com.example.cryptoapp_retrofix_rxjava_kotlin.service.CryptoAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() , RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://www.binance.com/api/v3/"
    private var cryptoModels: ArrayList<CryptoModel>?  = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private lateinit var binding: ActivityMainBinding

    //RxJava Disposable
    private var disposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        disposable = CompositeDisposable()

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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) //integrate RxJava to retrofit
            .build()//build object
            .create(CryptoAPI::class.java) // connect API class to retrofit

        // add call to composite disposable
        disposable?.add(retrofit.getData()  // pass retrofit call to get data
            .subscribeOn(Schedulers.io())  // listen to data in the background using RxJava thread
            .observeOn(AndroidSchedulers.mainThread()) // process data in Main Thread
            .subscribe(this::handleResponse) // pass response to handleResponse function
        )


        //connect API to retrofit
//        val service = retrofit.create(CryptoAPI::class.java)

//        val call = service.getData()
        //send the call asynchronously and receive response in callback function
//        call.enqueue(object: Callback<List<CryptoModel>> {
//            override fun onResponse(
//                call: Call<List<CryptoModel>>,
//                response: Response<List<CryptoModel>>
//            ) {
//                if (response.isSuccessful) {
//                    //if body is not null
//                    response.body()?.let { it ->
//                        cryptoModels = ArrayList(it)
//
//                        cryptoModels?.let {model->
//                            recyclerViewAdapter =
//                                RecyclerViewAdapter(model, this@MainActivity)
//                                binding.recyclerView.adapter = recyclerViewAdapter
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
//                //in case of error, print error
//                t.printStackTrace()
//            }
//
//        })
    }

    //process the response sent by load data
    private fun handleResponse(cryptoList : List<CryptoModel>) {
        cryptoModels = ArrayList(cryptoList)
        //if response is not null
        cryptoModels?.let { model->

            //pass data to recycler view adapter
            recyclerViewAdapter = RecyclerViewAdapter(model, this@MainActivity)
            //set recycler view adapter
            binding.recyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.symbol}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        //clear disposable
        disposable?.clear()
    }
}