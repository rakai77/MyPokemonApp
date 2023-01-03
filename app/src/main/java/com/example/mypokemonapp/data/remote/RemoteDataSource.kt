package com.example.mypokemonapp.data.remote

import androidx.paging.PagingSource
import com.example.mypokemonapp.data.remote.network.ApiResponse
import com.example.mypokemonapp.data.remote.response.PokemonResponse
import com.example.mypokemonapp.data.remote.response.PokemonResultResponse
import com.example.mypokemonapp.data.remote.response.PokemonSpeciesResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getPokemonList(): PagingSource<Int, PokemonResultResponse.ResultsItem>

    fun getDetailPokemon(pokemonName: String): Flow<ApiResponse<PokemonResponse>>

    fun getPokemonSpecies(pokemonName: String): Flow<ApiResponse<PokemonSpeciesResponse>>
}