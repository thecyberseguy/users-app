package thecyberseguy.usersapp.extensions

import android.os.Build
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.skydoves.androidveil.VeilRecyclerFrameView
import thecyberseguy.usersapp.R
import thecyberseguy.usersapp.constants.ErrorCode
import thecyberseguy.usersapp.customview.VeilViewItemDecoration
import thecyberseguy.usersapp.utils.Tools

fun AppCompatActivity.addOnBackPressedDispatcher(onBackPressed: () -> Unit = { finish() }) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
            onBackPressed.invoke()
        }
    } else {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed.invoke()
                }
            }
        )
    }
}

fun View.showErrorApiSnackbar(errorCode: Int, onRetry: () -> Unit): Snackbar {
    val msgId = Tools.getErrorResId(errorCode)
    val duration = if (errorCode == ErrorCode.NO_INTERNET_CONNECTION) {
        Snackbar.LENGTH_INDEFINITE
    } else {
        Snackbar.LENGTH_LONG
    }

    val snackbar = Snackbar.make(this, msgId, duration)
    snackbar.setBackgroundTint(context.getColor(R.color.red))
    snackbar.setActionTextColor(context.getColor(R.color.white))

    when (errorCode) {
        ErrorCode.NO_INTERNET_CONNECTION, ErrorCode.INTERNAL_SERVER_ERROR -> {
            snackbar.setAction(R.string.retry) {
                onRetry.invoke()
            }
        }
    }

    return snackbar
}

fun VeilRecyclerFrameView.toVerticalList(
    viewAdapter: RecyclerView.Adapter<*>,
    orientation: Int = RecyclerView.VERTICAL,
    isHasFixedSize: Boolean = true,
    withDivider: Boolean = false
) {
    setAdapter(viewAdapter)
    setLayoutManager(LinearLayoutManager(context, orientation, false))
    addVeiledItems(6)
    getRecyclerView().apply {
        setHasFixedSize(isHasFixedSize)
        clipToPadding = false
        if (withDivider) addItemDecoration(VeilViewItemDecoration(context))
    }
}

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}