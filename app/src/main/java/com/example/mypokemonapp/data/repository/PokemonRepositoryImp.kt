package com.example.mypokemonapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mypokemonapp.data.remote.RemoteDataSource
import com.example.mypokemonapp.data.Resource
import com.example.mypokemonapp.data.remote.network.ApiResponse
import com.example.mypokemonapp.data.remote.response.PokemonResponse
import com.example.mypokemonapp.data.remote.response.PokemonResultResponse
import com.example.mypokemonapp.data.remote.response.PokemonSpeciesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PokemonRepository{
    override fun getPokemonList(): Flow<PagingData<PokemonResultResponse.ResultsItem>> = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = {remoteDataSource.getPokemonList()}
    ).flow

    override fun getDetailPokemon(pokemonName: String): Flow<Resource<PokemonResponse>> =
        flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.getDetailPokemon(pokemonName).first()) {
                is ApiResponse.Success -> {
                    val data = response.data
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
                is ApiResponse.Empty -> {

                }
            }
        }

    override fun getPokemonSpecies(pokemonName: String): Flow<Resource<PokemonSpeciesResponse>> =
        flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.getPokemonSpecies(pokemonName).first()) {
                is ApiResponse.Success -> {
                    val data = response.data
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
                is ApiResponse.Empty -> {

                }
            }
        }
}