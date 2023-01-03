package com.example.mypokemonapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.palette.graphics.Palette
import coil.load
import com.example.mypokemonapp.R
import com.example.mypokemonapp.data.remote.response.PokemonResponse
import com.example.mypokemonapp.data.remote.response.PokemonResultResponse
import com.example.mypokemonapp.ui.home.HomeFragmentDirections
import com.google.android.material.card.MaterialCardView

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("navigate_to_pokemon_detail")
    fun navigateMoveToDetail(view: MaterialCardView, pokemonId: String) {
        view.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(pokemonId)
            view.findNavController().navigate(action)
        }
    }

    @JvmStatic
    @BindingAdapter("palette_image", "palette_card")
    fun homePokemonImagePalette(view: ImageView, url: String, paletteCard: MaterialCardView){
        view.load(extractPokemonImage(url)) {
            allowHardware(false)
            crossfade(200)
            error(R.drawable.ic_launcher_foreground)
            listener { _, result ->
                Palette.Builder(result.drawable.toBitmap()).generate() { palette ->
                    val rgb = palette?.dominantSwatch?.rgb
                    rgb?.let {
                        paletteCard.setCardBackgroundColor(rgb)
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("lower_to_upper")
    fun lowerToUpper(view: TextView, data: PokemonResultResponse.ResultsItem) {
        view.text = data.name.replaceFirstChar { it.uppercase() }
    }

}