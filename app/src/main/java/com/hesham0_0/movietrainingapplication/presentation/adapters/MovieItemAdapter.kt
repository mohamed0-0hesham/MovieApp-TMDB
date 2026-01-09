package com.hesham0_0.movietrainingapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hesham0_0.movietrainingapplication.databinding.MovieItemBinding
import com.hesham0_0.movietrainingapplication.domain.models.MovieItem

class MovieItemAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MovieItemAdapter.MyViewHolder>() {
    private var itemList: List<MovieItem> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.itemListXml = itemList[position]
    }

    override fun getItemCount(): Int = itemList.size

    fun setData(itemList: List<MovieItem>, addList:Boolean){
        if (addList) {
            this.itemList += itemList
        }
        else{
            this.itemList = itemList
        }
        notifyDataSetChanged()
    }

    inner class MyViewHolder(var binding: MovieItemBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition, itemList[bindingAdapterPosition])
            }
        }
    }
}














