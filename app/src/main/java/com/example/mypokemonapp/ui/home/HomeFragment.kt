package com.example.mypokemonapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.mypokemonapp.R
import com.example.mypokemonapp.databinding.FragmentHomeBinding
import com.example.mypokemonapp.ui.adapter.LoadingStateAdapter
import com.example.mypokemonapp.ui.adapter.PokemonPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PokemonPagingAdapter() }
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.pokemonList.collect {
                adapter.submitData(it)
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            rvPokemonList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { adapter.retry() },
                footer =  LoadingStateAdapter { adapter.retry()}
            )
            btnErrorLoad.setOnClickListener { adapter.retry()}

            adapter.addLoadStateListener { load ->
                pbPokemonList.isVisible = load.source.refresh is LoadState.Loading
                tvErrorLoad.isVisible = load.source.refresh is LoadState.Error
                btnErrorLoad.isVisible = load.refresh is LoadState.Error
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}