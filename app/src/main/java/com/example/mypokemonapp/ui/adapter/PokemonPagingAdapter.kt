package com.example.mypokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemonapp.data.remote.response.PokemonResultResponse
import com.example.mypokemonapp.databinding.ItemListPokemonBinding

class PokemonPagingAdapter : PagingDataAdapter<PokemonResultResponse.ResultsItem, PokemonPagingAdapter.PokemonViewHolder>(DIFF_CALLBACK) {

    class PokemonViewHolder(private val binding: ItemListPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: PokemonResultResponse.ResultsItem) {
            binding.pokemonResults = result
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        pokemon?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemListPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonResultResponse.ResultsItem>() {
            override fun areItemsTheSame(
                oldItem: PokemonResultResponse.ResultsItem,
                newItem: PokemonResultResponse.ResultsItem
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PokemonResultResponse.ResultsItem,
                newItem: PokemonResultResponse.ResultsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}