package thecyberseguy.usersapp.ui.home

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import thecyberseguy.usersapp.bases.BaseFragment
import thecyberseguy.usersapp.constants.APICode
import thecyberseguy.usersapp.databinding.FragmentHomeBinding
import thecyberseguy.usersapp.extensions.observeData
import thecyberseguy.usersapp.extensions.showUserDetails
import thecyberseguy.usersapp.extensions.toVerticalList
import thecyberseguy.usersapp.extensions.visibleOrGone
import thecyberseguy.usersapp.models.User
import thecyberseguy.usersapp.network.APIResult
import thecyberseguy.usersapp.ui.main.MainViewModel
import thecyberseguy.usersapp.utils.DataUtil

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val vm by viewModels<MainViewModel>()
    private val adapter by lazy { UsersAdapter() }

    override fun setupView() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            loadData()
            userList.toVerticalList(adapter)
            adapter.setItemListener {
                showUserDetails(it)
            }
        }
    }

    private fun loadData() {
        vm.loadUsers()
    }

    override fun onObserveData() {
        observeData(vm.errorApi, ::handleErrorApi)
        observeData(vm.users, ::handleUsersResult)
    }

    override fun onRetry(apiCode: String?) {
        if (apiCode == APICode.LOAD_USERS) loadData()
    }

    private fun handleUsersResult(result: APIResult<String>) {
        when (result) {
            is APIResult.Loading -> binding?.userList?.veil()
            is APIResult.Success -> binding?.userList?.unVeil().also {
                DataUtil.toList<User>(result.data).let {
                    adapter.setData(it)
                    checkTotalItemInAdapter()
                }
            }
            is APIResult.Error -> binding?.userList?.unVeil().also {
                vm.setError(result)
                checkTotalItemInAdapter()
            }
        }
    }

    private fun checkTotalItemInAdapter() {
        binding?.userList?.visibleOrGone(adapter.itemCount > 0)
        binding?.tvEmptyState?.visibleOrGone(adapter.itemCount == 0)
    }

}