package thecyberseguy.usersapp.ui.home

import thecyberseguy.usersapp.R
import thecyberseguy.usersapp.bases.BaseAdapter
import thecyberseguy.usersapp.databinding.ItemUserBinding
import thecyberseguy.usersapp.extensions.visibleOrGone
import thecyberseguy.usersapp.models.User

class UsersAdapter : BaseAdapter<User, ItemUserBinding>() {

    override fun getLayout() = R.layout.item_user

    override fun onBindViewHolder(holder: Companion.BaseViewHolder<ItemUserBinding>, position: Int) {
        holder.binding.apply {
            item = items[position]
            bottomSpace.visibleOrGone(position == items.indices.last)
            cardLayout.setOnClickListener {
                clickListener.invoke(items[position])
            }
        }
    }

}