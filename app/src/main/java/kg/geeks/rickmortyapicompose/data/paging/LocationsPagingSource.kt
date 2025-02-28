package kg.geeks.rickmortyapicompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import coil3.network.HttpException
import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocationModel
import java.io.IOException

class LocationsPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ResponseLocationModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseLocationModel> {
        val page = params.key ?: 1
        return try {
            val response = apiService.fetchAllLocations(page)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()?.results ?: emptyList()
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (data.isEmpty()) null else page + 1

                return Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception(response.message()))
            }

        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseLocationModel>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }
}