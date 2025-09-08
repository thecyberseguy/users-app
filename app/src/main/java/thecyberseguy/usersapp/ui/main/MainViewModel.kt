package thecyberseguy.usersapp.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import thecyberseguy.usersapp.bases.BaseViewModel
import thecyberseguy.usersapp.constants.APICode
import thecyberseguy.usersapp.constants.Url
import thecyberseguy.usersapp.models.User
import thecyberseguy.usersapp.network.APIRepository
import thecyberseguy.usersapp.network.APIResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiRepo: APIRepository) : BaseViewModel() {

    private val _users = Channel<APIResult<String>>(Channel.BUFFERED)
    val users = _users.receiveAsFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _actionClick = Channel<ActionClick>()
    val actionClick = _actionClick.receiveAsFlow()

    fun loadUsers() = viewModelScope.launch {
        val url = Url.USERS
        val apiCode = APICode.LOAD_USERS

        apiRepo.get(url = url, apiCode = apiCode)
            .onStart { _users.send(APIResult.Loading()) }
            .collect { _users.send(it) }
    }

    fun updateUserData(user: User) = viewModelScope.launch {
        _user.update { user }
    }

    fun onClickCloseButton() = viewModelScope.launch {
        _actionClick.send(ActionClick.GoBack)
    }

    sealed class ActionClick {
        data object GoBack : ActionClick()
    }

}