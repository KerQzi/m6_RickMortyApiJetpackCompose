package kg.geeks.rickmortyapicompose.data.repository

import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.data.dto.ResponseEpisodeModel

class EpisodeRepository(
    private val apiService: ApiService
) {
    suspend fun fetchEpisodes(): List<ResponseEpisodeModel>? {
        val response = apiService.fetchAllEpisodes()
        return if (response.isSuccessful) response.body()?.results else null
    }

    suspend fun fetchEpisodeDetail(id: Int): ResponseEpisodeModel? {
        val response = apiService.fetchEpisode(id)
        return if (response.isSuccessful) response.body() else null
    }
}