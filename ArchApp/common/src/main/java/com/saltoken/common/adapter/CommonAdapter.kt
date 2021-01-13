package com.saltoken.common.adapter

import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Created by zchu on 16-12-1.
 */

abstract class CommonAdapter<T> @JvmOverloads constructor(
    layoutResId: Int = 0,
    data: MutableList<T>? = null
) : BaseQuickAdapter<T, CommonViewHolder>(layoutResId, data) {

    constructor(data: MutableList<T>? = null) : this(0, data)

    override fun createBaseViewHolder(view: View): CommonViewHolder {
        return CommonViewHolder(view)
    }

    public override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return super.onCreateDefViewHolder(parent, viewType)
    }
}
