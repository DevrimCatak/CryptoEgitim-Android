package com.devrimcatak.egitim.ui.exchanges

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devrimcatak.egitim.R
import com.devrimcatak.egitim.databinding.ItemExchangesBinding
import com.devrimcatak.egitim.model.exchanges.Data


/**
 * Created by @Devrim Çatak on 9.07.2022.
 */
class ExchangesAdapter (private val context: Context, private val exchangesList: List<Data>, private val listener: ItemClickListener):
    RecyclerView.Adapter<ExchangesAdapter.MViewHolder>() {

    class MViewHolder(private val binding: ItemExchangesBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, listener: ItemClickListener, exchanges: Data){
            binding.onItemClickListener = listener
            binding.exchange = exchanges
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) :MViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemExchangesBinding.inflate(layoutInflater,parent,false)
                return MViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int){
        holder.bind(context, listener, exchangesList[position])
    }

    override fun getItemCount() : Int{
        return exchangesList.size
    }

}