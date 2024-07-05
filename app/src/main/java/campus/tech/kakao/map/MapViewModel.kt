package campus.tech.kakao.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MapRepository(application)

    val searchQuery = MutableLiveData<String>()
    private val _searchResults = MutableLiveData<List<MapItem>>()
    val searchResults: LiveData<List<MapItem>> get() = _searchResults

    private val _selectedItems = MutableLiveData<List<MapItem>>(emptyList())
    val selectedItems: LiveData<List<MapItem>> get() = _selectedItems

    init {
        searchQuery.observeForever { query ->
            searchItems(query)
        }
    }

    private fun searchItems(query: String) {
        viewModelScope.launch {
            val results = repository.searchItems(query)
            _searchResults.postValue(results)
        }
    }

    fun selectItem(item: MapItem) {
        _selectedItems.value = _selectedItems.value?.plus(item)
    }

    fun removeSelectedItem(item: MapItem) {
        _selectedItems.value = _selectedItems.value?.minus(item)
    }
}
