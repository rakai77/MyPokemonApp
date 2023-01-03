package com.example.mypokemonapp.utils

//extract pokemon image
fun extractPokemonImage(url: String): String {
    val index = url.split("/".toRegex()).dropLast(1).last()
    return  "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$index.png"
}