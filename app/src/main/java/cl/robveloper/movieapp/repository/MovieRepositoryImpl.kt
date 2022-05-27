package cl.robveloper.movieapp.repository

import cl.robveloper.movieapp.data.model.MovieList
import cl.robveloper.movieapp.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(private val dataSourceRemote: RemoteMovieDataSource) : MovieRepository {

    override suspend fun getUpcomingMovies(): MovieList = dataSourceRemote.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSourceRemote.getTopRatedMovies()

    override suspend fun getPopularMovies(): MovieList = dataSourceRemote.getPopularMovies()

}