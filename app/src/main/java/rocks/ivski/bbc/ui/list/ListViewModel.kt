package rocks.ivski.bbc.ui.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rocks.ivski.bbc.data.dto.Character
import rocks.ivski.bbc.data.repo.Repository
import rocks.ivski.bbc.navigation.Navigator
import rocks.ivski.bbc.ui.base.BaseViewModel

class ListViewModel(private val repo: Repository, private val navigator: Navigator) : BaseViewModel() {

    private val _state = MutableStateFlow<ListState>(ListState.Loading)
    val state: StateFlow<ListState>
        get() = _state

    fun handleIntent(intent: ListIntent) {
        when (intent) {
            ListIntent.LoadList -> viewModelScope.launch {
                _state.emit(ListState.Loading)
                handleApiResponse(repo.getCharacters(), ::onListLoaded)
            }
            is ListIntent.SelectCharacter -> viewModelScope.launch {
                _state.emit(ListState.CharacterSelected(intent.character))
                navigator.navigateTo(Navigator.NavTarget.Detail)
            }
        }
    }

    private fun onListLoaded(characters: List<Character>) {
        Log.e("aaa", "list loaded ${characters.size}")
        viewModelScope.launch {
            _state.emit(ListState.Ready(characters))
        }
    }

    sealed class ListIntent {
        object LoadList : ListIntent()
        data class SelectCharacter(val character: Character): ListIntent()
    }

    sealed class ListState {
        object Loading : ListState()
        data class Ready(val characters: List<Character>) : ListState()
        object NetworkError : ListState()
        object GenericError : ListState()
        data class CharacterSelected(val character: Character): ListState()
    }
}