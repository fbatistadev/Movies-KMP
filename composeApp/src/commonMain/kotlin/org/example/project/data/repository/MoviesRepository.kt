package org.example.project.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.example.project.data.network.KtorApiClient
import org.example.project.domain.model.MovieSection
import org.example.project.domain.model.toModel

class MoviesRepository(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getMovieSections(): List<MovieSection> {
        return withContext(ioDispatcher) {
            val popularMoviesDeferred = async { KtorApiClient.getMovies("popular") }
            val topRatedMoviesDeferred = async { KtorApiClient.getMovies("top_rated") }
            val upcomingMoviesDeferred = async { KtorApiClient.getMovies("upcoming") }

            val popularMovies = popularMoviesDeferred.await()
            val topRatedMovies = topRatedMoviesDeferred.await()
            val upcomingMovies = upcomingMoviesDeferred.await()

            listOf(
                MovieSection(
                    sectionType = MovieSection.SectionType.POPULAR,
                    movies = popularMovies.results.map {
                        it.toModel()
                    }
                ),
                MovieSection(
                    sectionType = MovieSection.SectionType.TOP_RATED,
                    movies = topRatedMovies.results.map {
                        it.toModel()
                    }
                ),
                MovieSection(
                    sectionType = MovieSection.SectionType.UPCOMING,
                    movies = upcomingMovies.results.map {
                        it.toModel()
                    }
                ),
            )
        }
    }
}