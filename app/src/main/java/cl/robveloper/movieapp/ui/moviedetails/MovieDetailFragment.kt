package cl.robveloper.movieapp.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import cl.robveloper.movieapp.R
import cl.robveloper.movieapp.databinding.FragmentMovieDetailBinding
import com.bumptech.glide.Glide

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding:FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}").centerCrop().into(binding.imgMovie)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.backroundImageUrl}").centerCrop().into(binding.imgBackround)
        binding.txtDescription.text = args.overview
        binding.txtMovieTitle.text = args.title
        binding.txtLanguage.text = "Language ${args.language}"
        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
        binding.txtRelease.text = "Released ${args.releaseDate}"
    }

}