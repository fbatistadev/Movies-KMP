package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.data.network.model.MoviesListResponse

private const val BASE_URL = "https://api.themoviedb.org"
const val IMAGE_SMALL_BASE_URL = "https://image.tmdb.org/t/p/w154"

class KtorApiClient {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZjRhMzM2ZjdjODYxZDcwYmVjM2Y1MTE4ZWI4MDJhZSIsIm5iZiI6MTYzOTA4NjU5NS44MTQwMDAxLCJzdWIiOiI2MWIyN2EwMzMyNmVjMTAwODI3ZDRkMzkiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.lv0kqYt27LkfVpXM2nzSHVtBPhDurPiDkbhhpEILZDQ",
                        refreshToken = ""
                    )
                }
            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    suspend fun getMovies(category: String, language: String = "pt-BR"): MoviesListResponse {
        return client.get("$BASE_URL/3/movie/$category") {
            parameter("language", language)
        }.body()
    }
}