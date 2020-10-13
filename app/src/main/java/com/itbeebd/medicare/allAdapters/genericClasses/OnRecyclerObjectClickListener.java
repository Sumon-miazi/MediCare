package com.itbeebd.medicare.allAdapters.genericClasses;

public interface OnRecyclerObjectClickListener<T> extends BaseRecyclerListener {
    void onItemClicked(T item);
}
