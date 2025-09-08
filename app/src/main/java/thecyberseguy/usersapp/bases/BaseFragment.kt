package thecyberseguy.usersapp.bases

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import thecyberseguy.usersapp.extensions.showErrorApiSnackbar
import thecyberseguy.usersapp.interfaces.OnLostConnection

abstract class BaseFragment<B : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment(), OnLostConnection {

    var binding: B? = null

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate.invoke(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        onObserveData()
    }

    override fun onRetry(apiCode: String?) {
        onRetryRequest(apiCode)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
        snackbar?.dismiss()
    }

    open fun setupView() {}

    open fun onObserveData() {}

    open fun onRetryRequest(apiCode: String?) {}

//    protected fun dismissFragment() {
//        findNavController().navigateUp()
//    }

    protected fun handleErrorApi(error: Pair<String?, Int>) {
        val container = activity?.findViewById<View>(R.id.content)

        snackbar = container?.showErrorApiSnackbar(error.second) { onRetry(error.first) }
        snackbar?.show()
    }

}