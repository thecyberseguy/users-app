package thecyberseguy.usersapp.ui.details

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import thecyberseguy.usersapp.R
import thecyberseguy.usersapp.bases.BaseBottomSheet
import thecyberseguy.usersapp.databinding.BottomSheetUserDetailsBinding
import thecyberseguy.usersapp.extensions.observeData
import thecyberseguy.usersapp.ui.main.MainViewModel

@AndroidEntryPoint
class UserDetailsBottomSheet : BaseBottomSheet<BottomSheetUserDetailsBinding>(
    BottomSheetUserDetailsBinding::inflate
) {

    private val args by navArgs<UserDetailsBottomSheetArgs>()
    private val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Theme_UsersApp_BottomSheetTheme)
    }

    override fun setupView() {
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = vm
        vm.updateUserData(args.user)
    }

    override fun onObserveData() {
        observeData(vm.actionClick, ::handleActionClick)
    }

    private fun handleActionClick(action: MainViewModel.ActionClick) {
        if (action == MainViewModel.ActionClick.GoBack) dismiss()
    }

}