package kg.geeks.rickmortyapicompose.ui.screens.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.geeks.rickmortyapicompose.data.dto.LocationFilter
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocationModel
import kg.geeks.rickmortyapicompose.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    private val _filter = MutableStateFlow(LocationFilter())
    val filter = _filter.asStateFlow()

    private val _locationsFlow =
        MutableStateFlow<PagingData<ResponseLocationModel>>(PagingData.empty())
    val locationsFlow = _locationsFlow.asStateFlow()

    private val _locationDetailFlow = MutableStateFlow<ResponseLocationModel?>(null)
    val locationDetailFlow: StateFlow<ResponseLocationModel?> = _locationDetailFlow.asStateFlow()

    init {
        fetchLocations()
    }

    fun updateFilter(newFilter: LocationFilter) {
        _filter.value = newFilter
        fetchLocations()
    }

    fun resetFilter() {
        _filter.value = LocationFilter()
        fetchLocations()
    }

    fun fetchLocations() = viewModelScope.launch {
        val currentFilter = _filter.value
        repository.fetchLocations(
            name = currentFilter.name,
            type = currentFilter.type,
            dimension = currentFilter.dimension
        ).flow
            .cachedIn(viewModelScope)
            .collectLatest { _locationsFlow.value = it }
    }

    fun fetchLocationDetail(id: Int) = viewModelScope.launch {
        repository.fetchLocationDetail(id)
            .catch { _locationDetailFlow.value = null }
            .collect { detail ->
                _locationDetailFlow.value = detail
            }
    }
}