package thecyberseguy.usersapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.withSave
import androidx.recyclerview.widget.RecyclerView
import thecyberseguy.usersapp.R
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
internal class VeilViewItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    private val mDivider = ContextCompat.getDrawable(context, R.drawable.item_divider)
    private val mBounds = Rect()

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.withSave {
            val left: Int
            val right: Int

            if (parent.clipToPadding) {
                left = parent.paddingLeft
                right = parent.width - parent.paddingRight
                clipRect(
                    left,
                    parent.paddingTop,
                    right,
                    parent.height - parent.paddingBottom
                )
            } else {
                left = 0
                right = parent.width
            }

            val childCount = parent.childCount

            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + child.translationY.roundToInt()
                val top = bottom - (mDivider?.intrinsicHeight ?: 1)

                mDivider?.setBounds(left, top, right, bottom)
                mDivider?.draw(this)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.setEmpty()
        } else {
            outRect[0, 0, 0] = mDivider?.intrinsicHeight ?: 1
        }
    }

}