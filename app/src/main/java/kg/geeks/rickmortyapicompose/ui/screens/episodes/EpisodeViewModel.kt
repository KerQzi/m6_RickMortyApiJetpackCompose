package kg.geeks.rickmortyapicompose.ui.screens.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.geeks.rickmortyapicompose.data.dto.ResponseEpisodeModel
import kg.geeks.rickmortyapicompose.data.repository.EpisodeRepository
import kotlinx.coroutines.launch

class EpisodeViewModel(
    private val repository: EpisodeRepository
): ViewModel() {

    private val _episodes = MutableLiveData<List<ResponseEpisodeModel>>()
    val episodes: LiveData<List<ResponseEpisodeModel>> = _episodes

    init {
        fetchEpisodes()
    }

    private fun fetchEpisodes(){
        viewModelScope.launch {
            _episodes.postValue(repository.fetchEpisodes())
        }
    }

    fun fetchEpisodeDetail(id: Int, onResult: (ResponseEpisodeModel?) -> Unit){
        viewModelScope.launch {
            onResult(repository.fetchEpisodeDetail(id))
        }
    }
}