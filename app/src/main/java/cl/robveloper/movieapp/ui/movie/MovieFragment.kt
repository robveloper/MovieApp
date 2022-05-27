package cl.robveloper.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import cl.robveloper.movieapp.R
import cl.robveloper.movieapp.core.Resource
import cl.robveloper.movieapp.data.local.AppDatabase
import cl.robveloper.movieapp.data.local.LocalMovieDataSource
import cl.robveloper.movieapp.data.model.Movie
import cl.robveloper.movieapp.data.remote.RemoteMovieDataSource
import cl.robveloper.movieapp.databinding.FragmentMovieBinding
import cl.robveloper.movieapp.presentation.MovieViewModel
import cl.robveloper.movieapp.presentation.MovieViewModelFactory
import cl.robveloper.movieapp.repository.MovieRepositoryImpl
import cl.robveloper.movieapp.repository.RetrofitClient
import cl.robveloper.movieapp.ui.movie.adapter.MovieAdapter
import cl.robveloper.movieapp.ui.movie.adapter.concat.PopularConcatAdapter
import cl.robveloper.movieapp.ui.movie.adapter.concat.TopRatedConcatAdapter
import cl.robveloper.movieapp.ui.movie.adapter.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }

                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "${result.exception}")
                }
            }
        })

    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment3(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}