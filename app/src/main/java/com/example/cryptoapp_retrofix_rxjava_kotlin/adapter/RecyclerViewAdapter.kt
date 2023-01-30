package com.example.cryptoapp_retrofix_rxjava_kotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp_retrofix_rxjava_kotlin.R
import com.example.cryptoapp_retrofix_rxjava_kotlin.databinding.RowLayoutBinding
import com.example.cryptoapp_retrofix_rxjava_kotlin.model.CryptoModel


class RecyclerViewAdapter (private val cryptoList : ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    //create a listener interface for clicks
    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }
    //get a background color palette
    private val colors: Array<String> = arrayOf("#FBED85","#C2D3CC","#A0DBF7","#E0F7A0","#DCC4FA","#FFB2B2")

    //accept a binding object for view binding
    class RowHolder(private val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        //create a bind 
        fun bind(cryptoModel:CryptoModel, colors:Array<String>,position: Int, listener: Listener) = with(binding) {
            itemView.setOnClickListener{
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 6]))
            textName.text = cryptoModel.symbol
            textPrice.text = cryptoModel.price
        }
    }



    // create and return the view holder using recycler row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    //get the number of list items
    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }

}