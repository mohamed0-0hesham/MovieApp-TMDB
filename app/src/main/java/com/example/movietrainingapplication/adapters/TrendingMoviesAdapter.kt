package com.example.movietrainingapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movietrainingapplication.databinding.TrendingMovieItemBinding
import com.example.movietrainingapplication.models.MovieItem

class TrendingMoviesAdapter (
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TrendingMoviesAdapter.TrendingViewHolder>() {
    private var itemList: List<MovieItem> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            TrendingMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.binding.trendingItemXml = itemList[position]
    }

    override fun getItemCount(): Int = itemList.size

    fun setData(itemList: List<MovieItem>, addList:Boolean){
        if (addList) {
            this.itemList = this.itemList+itemList
        }
        else{
            this.itemList = itemList
        }
        notifyDataSetChanged()
    }

    inner class TrendingViewHolder(var binding: TrendingMovieItemBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition,itemList[adapterPosition])
            }
        }
    }
}
