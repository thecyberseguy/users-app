package thecyberseguy.usersapp.bases

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import thecyberseguy.usersapp.network.APIResult

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel : ViewModel(), Observable {

    private val _callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    private val _errorApi = Channel<Pair<String?, Int>>(Channel.BUFFERED)
    val errorApi = _errorApi.receiveAsFlow()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        _callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        _callbacks.remove(callback)
    }

    fun setError(result: APIResult<String>) = viewModelScope.launch {
        if (result.errorCode != null) _errorApi.send(result.apiCode to result.errorCode)
    }

}