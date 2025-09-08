package thecyberseguy.usersapp.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseBottomSheet<B : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> B
) : BottomSheetDialogFragment() {

    var binding: B? = null

    private var _snackbar: Snackbar? = null

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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
        _snackbar?.dismiss()
    }

    open fun setupView() {}

    open fun onObserveData() {}

}