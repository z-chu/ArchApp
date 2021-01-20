package com.github.zchu.archapp.gank

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.zchu.archapp.gakn.R
import com.github.zchu.archapp.gank.data.bean.CategoryResult
import com.saltoken.common.adapter.CommonAdapter
import com.saltoken.common.adapter.CommonViewHolder
import com.saltoken.commonbase.extensions.dp2px
import com.saltoken.commonbase.utils.DateUtil

class GankCategoryAdapter : CommonAdapter<CategoryResult.ResultsBean>(R.layout.list_item_gank) {
    override fun convert(holder: CommonViewHolder, item: CategoryResult.ResultsBean) {
        val imageView = holder.getView<ImageView>(R.id.iv_image)
        if (item.images != null && item.images.size > 0) {
            imageView.visibility = View.VISIBLE
            Glide
                .with(context)
                .load(getThumbnailUrl(item.images[0]))
                .placeholder(R.drawable.ic_default_placeholder)
                .into(imageView)
        } else {
            imageView.visibility = View.GONE
        }
        holder.setText(R.id.tv_title, item.desc)
        holder.setText(R.id.tv_who, item.who)
        holder.setText(R.id.tv_date, DateUtil.dateFormat(item.publishedAt))
    }

    private fun getThumbnailUrl(url: String): String {
        return url + "?imageView2/0/w/" + context.dp2px(80f)
    }

}