package io.github.lingnanlu.gaoxiaolian.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/28.
 */
public abstract class CommonViewHolder<T> extends RecyclerView.ViewHolder{

    public CommonViewHolder(Context context, ViewGroup root, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
        ButterKnife.bind(this, itemView);
    }

    public Context getContext() {
        return itemView.getContext();
    }


    public abstract void bindData(T t);

    public void setData(T t) {
        bindData(t);
    }
}
