package com.saltoken.common.adapter

import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by zchu on 16-12-1.
 */

abstract class CommonMultiItemAdapter<T : MultiItemEntity>(data: MutableList<T>?) :
    BaseMultiItemQuickAdapter<T, CommonViewHolder>(data) {


    override fun createBaseViewHolder(view: View): CommonViewHolder {
        return CommonViewHolder(view)
    }


}
