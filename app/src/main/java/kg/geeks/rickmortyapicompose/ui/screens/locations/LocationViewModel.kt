package kg.geeks.rickmortyapicompose.ui.screens.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocationModel
import kg.geeks.rickmortyapicompose.data.repository.LocationRepository
import kotlinx.coroutines.launch

class LocationViewModel(
    private val repository: LocationRepository
): ViewModel() {

    private val _locations = MutableLiveData<List<ResponseLocationModel>>()
    val locations: LiveData<List<ResponseLocationModel>> = _locations

    init {
        fetchLocations()
    }

    private fun fetchLocations(){
        viewModelScope.launch {
            _locations.postValue(repository.fetchLocations())
        }
    }

    fun fetchLocationDetail(id: Int, onResult: (ResponseLocationModel?) -> Unit){
        viewModelScope.launch {
            onResult(repository.fetchLocationDetail(id))
        }
    }
}