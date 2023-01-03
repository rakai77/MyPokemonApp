package com.example.mypokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemonapp.databinding.LoadingStateBinding

class LoadingStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {


    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = LoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding)
    }

    inner class LoadingStateViewHolder(private val binding: LoadingStateBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnLoad.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                pbLoadingState.isVisible = loadState is LoadState.Loading
                tvLoad.isVisible = loadState is LoadState.Error
                btnLoad.isVisible = loadState is LoadState.Error

                if (loadState is LoadState.Error) {
                    tvLoad.text = loadState.error.localizedMessage
                }
            }
        }
    }
}