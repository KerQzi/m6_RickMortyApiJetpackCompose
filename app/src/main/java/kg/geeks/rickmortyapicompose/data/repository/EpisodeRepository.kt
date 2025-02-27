package kg.geeks.rickmortyapicompose.data.repository

import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseEpisodeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EpisodeRepository(
    private val apiService: ApiService
) {
    fun fetchEpisodes() = flow {
        apiService.fetchAllEpisodes().body()?.results?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun fetchEpisodeDetail(id: Int) = flow<ResponseEpisodeModel?> {
        apiService.fetchEpisode(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}