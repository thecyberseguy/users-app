package thecyberseguy.usersapp.bases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VB : ViewDataBinding> :
    RecyclerView.Adapter<BaseAdapter.Companion.BaseViewHolder<VB>>() {

    companion object {
        class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) :
            RecyclerView.ViewHolder(binding.root)
    }

    val items = mutableListOf<T>()
    var clickListener: (item: T) -> Unit = {}

    abstract fun getLayout(): Int

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB>(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayout(),
            parent,
            false
        )
    )

    fun setData(list: List<T>?) {
        if (!list.isNullOrEmpty()) {
            items.clear()
            items.addAll(list)
        }
    }

    fun setItemListener(listener: (item: T) -> Unit) {
        clickListener = listener
    }

}