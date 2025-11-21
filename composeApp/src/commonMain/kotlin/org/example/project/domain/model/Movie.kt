package org.example.project.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val genres: List<Genre>?,
    val year: Int,
    val duration: String?,
    val rating: String,
    val castMembers: List<CastMember>?,
)

// fake objects
val movie1 = Movie(
    id = 1,
    title = "A Minecraft Movie",
    overview =  "Trying to leave their troubled lives behind, twin brothers return to their hometown to start again, only to discover that an even greater evil is waiting to welcome them back.",
    posterUrl = "url",
    genres = listOf(genre1, genre2),
    year = 2022,
    duration = "2h 36 min",
    rating = "8.5",
    castMembers = listOf(
        castMember1,
        castMember2,
    )
)