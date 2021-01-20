package com.saltoken.common.widget

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.zchu.common.util.ThemeUtils
import com.saltoken.common.R
import com.zyyoona7.itemdecoration.RecyclerViewDivider
import com.zyyoona7.itemdecoration.provider.LinearItemDecoration


object ItemDecorations {

    @JvmStatic
    fun listDivider(context: Context): RecyclerView.ItemDecoration {
        return listDividerBuilder(context).build()
    }

    @JvmStatic
    fun listDividerBuilder(context: Context): LinearItemDecoration.Builder {
        val resolveDrawable = ThemeUtils.resolveDrawable(context, android.R.attr.listDivider)!!
        return RecyclerViewDivider
            .linear()
            .drawable(resolveDrawable)
            .dividerSize(resolveDrawable.intrinsicHeight)
            .marginEnd(context.resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin))
            .marginStart(context.resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin))
            .hideAroundDividerForItemType(BaseQuickAdapter.HEADER_VIEW)
            .hideDividerForItemType(BaseQuickAdapter.LOAD_MORE_VIEW)
            .hideDividerForItemType(BaseQuickAdapter.EMPTY_VIEW)
            .hideLastDivider()
    }

}

fun RecyclerView.ItemDecoration.addTo(recyclerView: RecyclerView) {
    recyclerView.addItemDecoration(this)
}
