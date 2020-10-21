package com.itbeebd.medicare.allAdapters.genericClasses;

import android.view.View;

public interface OnRecyclerObjectClickListener<T> extends BaseRecyclerListener {
    void onItemClicked(T item, View view);
}
