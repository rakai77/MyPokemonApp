package com.example.mypokemonapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokemonapp.data.Resource
import com.example.mypokemonapp.data.remote.response.PokemonResponse
import com.example.mypokemonapp.data.remote.response.PokemonSpeciesResponse
import com.example.mypokemonapp.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemonDetailResponse = MutableLiveData<Resource<PokemonResponse>>()
    val pokemonDetailResponse: LiveData<Resource<PokemonResponse>> get() = _pokemonDetailResponse


    private val _pokemonSpeciesResponse = MutableLiveData<Resource<PokemonSpeciesResponse>>()
    val pokemonSpeciesResponse: LiveData<Resource<PokemonSpeciesResponse>> get() = _pokemonSpeciesResponse


    fun getDetailPokemonByName(pokemonName: String) {
        viewModelScope.launch {
            repository.getDetailPokemon(pokemonName)
                .onStart { _pokemonDetailResponse.postValue(Resource.Loading()) }
                .catch { error ->
                    error.message?.let {
                        _pokemonDetailResponse.postValue(Resource.Error(it))
                    }
                }
                .collectLatest { pokemonDetail ->
                    pokemonDetail.data?.let {
                        _pokemonDetailResponse.postValue(Resource.Success(it))
                    }
                }
        }
    }

    fun getSpeciesPokemonByName(pokemonName: String) {
        viewModelScope.launch {
            repository.getPokemonSpecies(pokemonName)
                .onStart { _pokemonSpeciesResponse.postValue(Resource.Loading()) }
                .catch { error ->
                    error.message?.let{
                        _pokemonSpeciesResponse.postValue(Resource.Error(it))
                    }
                }
                .collectLatest { pokemonSpecies ->
                    pokemonSpecies.data?.let {
                        _pokemonSpeciesResponse.postValue(Resource.Success(it))
                    }
                }
        }
    }
}