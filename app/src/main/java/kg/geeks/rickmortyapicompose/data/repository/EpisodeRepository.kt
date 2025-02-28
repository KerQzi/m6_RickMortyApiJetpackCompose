package kg.geeks.rickmortyapicompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseEpisodeModel
import kg.geeks.rickmortyapicompose.data.paging.CharactersPagingSource
import kg.geeks.rickmortyapicompose.data.paging.EpisodePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EpisodeRepository(
    private val apiService: ApiService
) {
    fun fetchEpisodes(): Pager<Int, ResponseEpisodeModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 100,
                initialLoadSize = 60,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { EpisodePagingSource(apiService) }
        )
    }

    fun fetchEpisodeDetail(id: Int) = flow<ResponseEpisodeModel?> {
        apiService.fetchEpisode(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}