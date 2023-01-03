package com.example.mypokemonapp.data.remote.network

import com.example.mypokemonapp.data.remote.response.PokemonResponse
import com.example.mypokemonapp.data.remote.response.PokemonResultResponse
import com.example.mypokemonapp.data.remote.response.PokemonSpeciesResponse
import com.example.mypokemonapp.utils.Constants.QUERY_LIMIT
import com.example.mypokemonapp.utils.Constants.QUERY_OFFSET
import com.example.mypokemonapp.utils.Constants.QUERY_POKEMON_NAME
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query(QUERY_OFFSET) offset: Int,
        @Query(QUERY_LIMIT) limit: Int
    ) : Response<PokemonResultResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path(QUERY_POKEMON_NAME) name: String
    ) : PokemonResponse

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(
        @Path(QUERY_POKEMON_NAME) name: String
    ) : PokemonSpeciesResponse
}