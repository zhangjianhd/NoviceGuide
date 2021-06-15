package com.zj.lib.noviceguidedemo;


import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zj on 2019-10-26 in project NoviceGuideDemo.
 */
public class ItemViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private T dataBinding;

    public ItemViewHolder(T dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
    }


    public T getDataBinding() {
        return dataBinding;
    }
}
