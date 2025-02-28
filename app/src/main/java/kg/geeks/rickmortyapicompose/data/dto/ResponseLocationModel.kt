package kg.geeks.rickmortyapicompose.data.dto

data class ResponseLocations(
    val results: List<ResponseLocationModel>? = null,
    val info: PageInfo? = null
)
data class ResponseLocationModel(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String>? = null,
    val url: String? = null,
    val created: String? = null
)