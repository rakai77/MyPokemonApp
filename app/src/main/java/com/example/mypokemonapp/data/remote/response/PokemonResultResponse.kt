package com.example.mypokemonapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonResultResponse(

	@field:SerializedName("count")
	val count: Int?,

	@field:SerializedName("next")
	val next: String?,

	@field:SerializedName("previous")
	val previous: Any?,

	@field:SerializedName("results")
	val results: List<ResultsItem>
){
	data class ResultsItem(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("url")
		val url: String
	)
}

