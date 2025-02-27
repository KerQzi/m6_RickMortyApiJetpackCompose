package kg.geeks.rickmortyapicompose.data.repository

import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocationRepository(
    private val apiService: ApiService
) {
    fun fetchLocations() = flow {
        apiService.fetchAllLocations().body()?.results?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun fetchLocationDetail(id: Int) = flow<ResponseLocationModel?> {
        apiService.fetchLocation(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}