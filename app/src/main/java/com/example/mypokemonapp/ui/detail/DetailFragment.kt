package com.example.mypokemonapp.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import coil.load
import com.example.mypokemonapp.R
import com.example.mypokemonapp.data.Resource
import com.example.mypokemonapp.data.remote.response.PokemonResponse
import com.example.mypokemonapp.data.remote.response.PokemonSpeciesResponse
import com.example.mypokemonapp.databinding.FragmentDetailBinding
import com.skydoves.rainbow.Rainbow
import com.skydoves.rainbow.RainbowOrientation
import com.skydoves.rainbow.color
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel : DetailViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()
    private var isLoading: Boolean? = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        setupViewModel()

    }

    private fun setupViewModel() {
        val pokemonName: String = args.pokemonName

        detailViewModel.getDetailPokemonByName(pokemonName)
        detailViewModel.pokemonDetailResponse.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Success -> {
                    pokemonDetail(result.data!!)
                    isLoading(false)
                }
                is Resource.Error -> {
                    isLoading(false)
                }
                is Resource.Loading -> {
                    isLoading(true)
                }
            }
        }

        detailViewModel.getSpeciesPokemonByName(pokemonName)
        detailViewModel.pokemonSpeciesResponse.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Success -> {
                    pokemonSpecies(result.data!!)
                    isLoading(false)
                }
                is Resource.Error -> {
                    isLoading(false)
                }
                is Resource.Loading -> {
                    isLoading(true)
                }
            }
        }
    }

    private fun pokemonDetail(data: PokemonResponse) {
        binding.apply {
            val pokemonImage = data.sprites?.other?.home?.frontDefault
            setBackgroundPalette(ivDetailPokemon, pokemonImage, shapeImageDetail)
            tvDetailPokemonNumber.text = getString(R.string.pokemon_number_format, data.id)
            tvDetailPokemonName.text = data.name.replaceFirstChar { it.uppercase() }

        }
    }

    private fun pokemonSpecies(data: PokemonSpeciesResponse) {
        binding.apply {
            var flavorText = data.flavorTextEntries[1].flavorText
            flavorText = flavorText.replace("POKéMON", "Pokémon")
            flavorText = flavorText.replace("\n", " ")
            tvDetailPokemonDesc.text = flavorText
        }
    }

    private fun setBackgroundPalette(view: ImageView, url: String?, shapeImage: View) {
        view.load(url) {
            allowHardware(false)
            crossfade(200)
            error(R.drawable.ic_launcher_foreground)
            listener(
                onSuccess = { _, result ->
                    Palette.Builder(result.drawable.toBitmap()).generate() { palette ->
                        val light = palette?.lightVibrantSwatch?.rgb
                        val domain = palette?.dominantSwatch?.rgb
                        if (domain != null) {
                            if (light != null) {
                                Rainbow(shapeImage).palette {
                                    +color(domain)
                                    +color(light)
                                }.background(orientation = RainbowOrientation.TOP_BOTTOM)
                            } else {
                                shapeImage.setBackgroundColor(domain)
                            }
                        }
                    }
                }
            )
        }
    }
    private fun isLoading(boolean: Boolean) {
        binding.apply {
            if (boolean) {
                isLoading = true
                pbPokemonDetail.visibility = View.VISIBLE
            } else {
                isLoading = false
                pbPokemonDetail.visibility = View.GONE
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}