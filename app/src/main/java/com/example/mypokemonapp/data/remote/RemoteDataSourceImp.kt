package com.example.mypokemonapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypokemonapp.data.remote.network.ApiResponse
import com.example.mypokemonapp.data.remote.network.ApiService
import com.example.mypokemonapp.data.remote.response.PokemonResponse
import com.example.mypokemonapp.data.remote.response.PokemonResultResponse
import com.example.mypokemonapp.data.remote.response.PokemonSpeciesResponse
import com.example.mypokemonapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override fun getPokemonList(): PagingSource<Int, PokemonResultResponse.ResultsItem> {
        return object : PagingSource<Int, PokemonResultResponse.ResultsItem>() {
            override fun getRefreshKey(state: PagingState<Int, PokemonResultResponse.ResultsItem>): Int? {
                return state.anchorPosition
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResultResponse.ResultsItem> {
                return try {
                    val pokemonOffset = params.key ?: Constants.OFFSET

                    val response = apiService.getPokemonList(pokemonOffset, Constants.LIMIT)
                    val pokemon = response.body()?.results ?: emptyList()

                    val prevKey =
                        if (pokemonOffset == Constants.OFFSET) null else pokemonOffset - Constants.LIMIT
                    val nextKey =
                        if (pokemon.size < 20 || pokemon.isEmpty()) null else pokemonOffset + Constants.LIMIT

                    LoadResult.Page(
                        data = pokemon,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                } catch (e: IOException) {
                    LoadResult.Error(e)
                } catch (e: HttpException) {
                    LoadResult.Error(e)
                }
            }

        }
    }

    override fun getDetailPokemon(pokemonName: String): Flow<ApiResponse<PokemonResponse>> {
        return flow {
            try {
                val response = apiService.getPokemonByName(pokemonName)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getPokemonSpecies(pokemonName: String): Flow<ApiResponse<PokemonSpeciesResponse>> {
        return flow {
            try {
                val response = apiService.getPokemonSpecies(pokemonName)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}