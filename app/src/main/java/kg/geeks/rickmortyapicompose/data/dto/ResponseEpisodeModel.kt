package kg.geeks.rickmortyapicompose.data.dto

data class ResponseEpisodes(
    val results: List<ResponseEpisodeModel>? = null,
    val info: PageInfo? = null
)
data class ResponseEpisodeModel(
    val id: Int? = null,
    val name: String? = null,
    val air_date: String? = null,
    val episode: String? = null,
    val characters: List<String>? = null,
    val url: String? = null,
    val created: String? = null
)