package kg.geeks.rickmortyapicompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocationModel
import kg.geeks.rickmortyapicompose.data.paging.LocationsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocationRepository(
    private val apiService: ApiService
) {
    fun fetchLocations(): Pager<Int, ResponseLocationModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 100,
                initialLoadSize = 60,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { LocationsPagingSource(apiService) }
        )
    }

    fun fetchLocationDetail(id: Int) = flow<ResponseLocationModel?> {
        apiService.fetchLocation(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}