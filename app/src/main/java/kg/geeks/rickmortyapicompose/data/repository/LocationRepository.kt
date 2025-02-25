package kg.geeks.rickmortyapicompose.data.repository

import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocationModel

class LocationRepository(
    private val apiService: ApiService
) {
    suspend fun fetchLocations(): List<ResponseLocationModel>? {
        val response = apiService.fetchAllLocations()
        return if (response.isSuccessful) response.body()?.results else null
    }

    suspend fun fetchLocationDetail(id: Int): ResponseLocationModel? {
        val response = apiService.fetchLocation(id)
        return if (response.isSuccessful) response.body() else null
    }
}