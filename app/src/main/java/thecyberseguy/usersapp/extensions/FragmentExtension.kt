package thecyberseguy.usersapp.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import thecyberseguy.usersapp.models.User
import thecyberseguy.usersapp.ui.home.HomeFragmentDirections

fun <T> Fragment.observeData(data: Flow<T>, action: (t: T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        data.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
            action(it)
        }
    }
}

fun Fragment.navigate(navDirections: NavDirections, options: NavOptions? = null) {
    try {
        findNavController().navigate(navDirections, options)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.showUserDetails(user: User) {
    val direction = HomeFragmentDirections.navigateToUserDetails(user)
    navigate(direction)
}