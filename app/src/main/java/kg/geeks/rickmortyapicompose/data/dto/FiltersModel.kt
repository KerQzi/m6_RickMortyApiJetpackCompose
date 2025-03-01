package kg.geeks.rickmortyapicompose.data.dto

data class CharacterFilter(
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val gender: String? = null
)

data class EpisodeFilter(
    val name: String? = null,
    val episode: String? = null
)

data class LocationFilter(
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null
)
