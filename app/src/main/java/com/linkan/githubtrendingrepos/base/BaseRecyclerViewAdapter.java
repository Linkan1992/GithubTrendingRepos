package com.linkan.githubtrendingrepos.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.linkan.githubtrendingrepos.databinding.ConnectionErrorRowLayoutBinding;

import java.util.List;


public abstract class BaseRecyclerViewAdapter<T, K extends BaseRecyclerViewAdapter.BaseViewHolder> extends RecyclerView.Adapter<K> {

    private EmptyErrorViewModel errorViewModel;

    private static final int VIEW_TYPE_EMPTY = 0;

    private static final int VIEW_TYPE_NORMAL = 1;


    // Allows to remember the last item shown on screen
    protected int lastPosition = -1;

    private BaseRecyclerViewAdapterListener mListener;

    protected List<T> data;

    public boolean loading = false;

    public BaseRecyclerViewAdapter(List<T> data) {
        this.data = data;
    }

    public interface BaseRecyclerViewAdapterListener {
        void onRetryClick();
    }

    public void setListener(BaseRecyclerViewAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case VIEW_TYPE_NORMAL:

                return mOnCreateViewHolder(parent, viewType);

            case VIEW_TYPE_EMPTY:

                ConnectionErrorRowLayoutBinding errorRowLayoutBinding = ConnectionErrorRowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return (K) new ConnectionErrorViewHolder(errorRowLayoutBinding);

            default:
                return mOnCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        if (holder != null && !data.isEmpty())
            holder.onBind(data.get(position));
        else if (data.isEmpty())
            holder.onBind(null);
    }

    @Override
    public int getItemCount() {
        if (!data.isEmpty()) {
            return data.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!data.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else
            return VIEW_TYPE_EMPTY;
    }

    public void addItems(List<T> repoList) {

        data.addAll(repoList);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return this.data;
    }



    public void clearItems() {
        data.clear();
    }


    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        protected abstract void onBind(T object);

        protected abstract void viewDetachedFromWindow();
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull K holder) {
        holder.viewDetachedFromWindow();
        super.onViewDetachedFromWindow(holder);
    }

    protected abstract K mOnCreateViewHolder(ViewGroup parent, int viewType);


    public void setLoading(boolean loading) {
        this.loading = loading;
    }


    public void setRetryVisibility(boolean visibility) {
        if (errorViewModel != null)
            errorViewModel.setIsVisible(visibility);
    }


    // Connection ViewHolder
    private class ConnectionErrorViewHolder extends BaseRecyclerViewAdapter<T, K>.BaseViewHolder implements RetryNavigator {

        private ConnectionErrorRowLayoutBinding mBinding;

        public ConnectionErrorViewHolder(ConnectionErrorRowLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        protected void onBind(T object) {
            errorViewModel = new EmptyErrorViewModel(this);

            if ((data != null && data.size() != 0))
                errorViewModel.setIsVisible(false);

            mBinding.setViewModel(errorViewModel);

        }

        @Override
        protected void viewDetachedFromWindow() {

        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }

    }

}
