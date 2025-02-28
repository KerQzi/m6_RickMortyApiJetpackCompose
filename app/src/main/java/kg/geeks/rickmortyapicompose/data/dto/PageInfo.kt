package kg.geeks.rickmortyapicompose.data.dto

data class PageInfo(
    val count: Int? = null,
    val pages: Int? = null,
    val next: String? = null,
    val prev: String? = null
)
