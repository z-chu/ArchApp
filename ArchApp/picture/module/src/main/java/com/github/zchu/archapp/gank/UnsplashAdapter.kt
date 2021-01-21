package com.github.zchu.archapp.gank

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.zchu.archapp.gank.data.bean.UnsplashImage
import com.github.zchu.common.util.getColorCompat
import com.saltoken.common.adapter.CommonAdapter
import com.saltoken.common.adapter.CommonViewHolder
import com.saltoken.common.widget.RatioImageView

class UnsplashAdapter : CommonAdapter<UnsplashImage>() {
    override fun convert(holder: CommonViewHolder, item: UnsplashImage) {
        val imageView = holder.itemView as RatioImageView
        imageView.setOriginalSize(item.width, item.height)
        if (item.urls != null) {
            Glide
                .with(context)
                .load(item.urls.small)
                .placeholder(ColorDrawable(context.getColorCompat(colorsArray[holder.layoutPosition % colorsArray.size])))
                .into(imageView)
        }
    }


    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val imageView = RatioImageView(parent.context)
        return CommonViewHolder(imageView)
    }

    companion object {
        val colorsArray = arrayOf(
            R.color.md_red_50,
            R.color.md_amber_50,
            R.color.md_blue_50,
            R.color.md_brown_50,
            R.color.md_deep_orange_50,
            R.color.md_green_50
        )
    }
}