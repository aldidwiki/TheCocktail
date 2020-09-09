package com.aldidwikip.thecocktail.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldidwikip.thecocktail.R
import com.aldidwikip.thecocktail.data.model.Cocktail
import com.aldidwikip.thecocktail.databinding.ListItemBinding

class CocktailListAdapter : ListAdapter<Cocktail, CocktailListAdapter.CocktailViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding: ListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))

            itemView.setOnClickListener {
                onItemClickListener.onItemClicked(getItem(adapterPosition))
            }
        }
    }

    class CocktailViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataCocktailModel: Cocktail) {
            binding.apply {
                cocktailData = dataCocktailModel
                executePendingBindings()
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cocktail>() {
            override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
                return oldItem.cocktailId == newItem.cocktailId
            }

            override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(dataCocktailModel: Cocktail)
    }
}

