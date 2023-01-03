package com.example.mypokemonapp.data.repository

import androidx.paging.PagingData
import com.example.mypokemonapp.data.Resource
import com.example.mypokemonapp.data.remote.response.PokemonResponse
import com.example.mypokemonapp.data.remote.response.PokemonResultResponse
import com.example.mypokemonapp.data.remote.response.PokemonSpeciesResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<PagingData<PokemonResultResponse.ResultsItem>>

    fun getDetailPokemon(pokemonName: String): Flow<Resource<PokemonResponse>>

    fun getPokemonSpecies(pokemonName: String): Flow<Resource<PokemonSpeciesResponse>>
}
