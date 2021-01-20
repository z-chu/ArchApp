package com.saltoken.common.listing;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.saltoken.common.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class CustomLoadMoreView extends BaseLoadMoreView {

    @NotNull
    @Override
    public View getLoadComplete(@NotNull BaseViewHolder baseViewHolder) {
        return Objects.requireNonNull(baseViewHolder.findView(R.id.load_more_load_complete_view));
    }

    @NotNull
    @Override
    public View getLoadEndView(@NotNull BaseViewHolder baseViewHolder) {
        return Objects.requireNonNull(baseViewHolder.findView(R.id.the_end_view));
    }

    @NotNull
    @Override
    public View getLoadFailView(@NotNull BaseViewHolder baseViewHolder) {
        return Objects.requireNonNull(baseViewHolder.findView(R.id.error_view));
    }

    @NotNull
    @Override
    public View getLoadingView(@NotNull BaseViewHolder baseViewHolder) {
        return Objects.requireNonNull(baseViewHolder.findView(R.id.loading_view));
    }

    @NotNull
    @Override
    public View getRootView(@NotNull ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_load_more, viewGroup, false);
    }
}
