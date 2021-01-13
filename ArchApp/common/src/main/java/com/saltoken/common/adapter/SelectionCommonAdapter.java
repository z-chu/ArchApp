package com.saltoken.common.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SelectionCommonAdapter<T> extends CommonAdapter<T> {

    private OnSelectedItemListener onSelectedItemListener;
    private T selectedItem = null;

    public SelectionCommonAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public SelectionCommonAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected void convert(@NotNull CommonViewHolder helper, T item) {
        convert(helper, item, getItemSelected(helper, item));
    }

    protected abstract void convert(CommonViewHolder helper, T item, boolean isSelected);

    protected boolean getItemSelected(@NonNull CommonViewHolder helper, @NonNull T item) {
        return ObjectsCompat.equals(this.selectedItem, item);
    }


    public int getSelectedPosition() {
        return getData().indexOf(selectedItem);
    }


    public void setSelectedAdapterPosition(int selectedPosition, boolean isNotifyListener) {
        if (selectedPosition < 0 || selectedPosition >= getData().size()) {
            return;
        }
        T newItem = getData().get(selectedPosition);
        if (ObjectsCompat.equals(this.selectedItem, newItem)) {
            return;
        }

        T oldItem = this.selectedItem;
        int oldPosition = getData().indexOf(oldItem);
        boolean hasOld = oldPosition > -1 && oldPosition < getData().size() + getHeaderLayoutCount();
        this.selectedItem = newItem;
        onSelectedItem(hasOld ? oldItem : null, newItem);

        if (isNotifyListener) {
            if (onSelectedItemListener != null) {
                onSelectedItemListener.onSelectedItem(this, hasOld ? oldPosition : null, selectedPosition);
            }
        }
        if (selectedPosition < getData().size() + getHeaderLayoutCount()) {
            notifyItemChanged(selectedPosition + getHeaderLayoutCount());
        } else {
            notifyDataSetChanged();
        }
        if (hasOld) {
            notifyItemChanged(oldPosition + getHeaderLayoutCount());
        }
    }

    public void setSelectedItem(T newItem, boolean isNotifyListener) {
        setSelectedAdapterPosition(getData().indexOf(newItem), isNotifyListener);
    }


    public void setSelectedAdapterPosition(int selectedPosition) {
        setSelectedAdapterPosition(selectedPosition, true);
    }


    protected void onSelectedItem(@Nullable T oldItem, @NonNull T newItem) {


    }


    @Nullable
    public T getSelectedItem() {
        return selectedItem;
    }

    public void reset() {
        selectedItem = null;
        notifyDataSetChanged();
    }

    public void setOnSelectedItemListener(OnSelectedItemListener onSelectedItemListener) {
        this.onSelectedItemListener = onSelectedItemListener;
    }

    public interface OnSelectedItemListener {
        void onSelectedItem(@NonNull SelectionCommonAdapter<?> adapter, @Nullable Integer oldPosition, int newPosition);
    }


}
