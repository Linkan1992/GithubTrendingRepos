package com.linkan.githubtrendingrepos.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkan.githubtrendingrepos.base.BaseRecyclerViewAdapter;
import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;
import com.linkan.githubtrendingrepos.databinding.TrendingRepoRowLayoutBinding;

import java.util.Collections;
import java.util.List;


public class TrendingRepoAdapter extends BaseRecyclerViewAdapter<TrendingRepo, TrendingRepoAdapter.TrendingRepoViewHolder> {

    private int previousExpandedPosition = -1, mExpandedPosition = -1;

    public TrendingRepoAdapter(List<TrendingRepo> data) {
        super(data);
    }

    @Override
    protected TrendingRepoViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrendingRepoAdapter.TrendingRepoViewHolder(TrendingRepoRowLayoutBinding
                .inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }


    public class TrendingRepoViewHolder extends BaseRecyclerViewAdapter<TrendingRepo, TrendingRepoAdapter.TrendingRepoViewHolder>.BaseViewHolder implements TrendingRepoNavigator {

        private TrendingRepoRowLayoutBinding mBinding;

        public TrendingRepoViewHolder(TrendingRepoRowLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        protected void onBind(TrendingRepo trendingRepo) {
            TrendingRepoViewModel trendingRepoViewModel = new TrendingRepoViewModel(this, trendingRepo);
            mBinding.setViewModel(trendingRepoViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();


            int position = getAdapterPosition();

            final boolean isExpanded = position == mExpandedPosition;

            if (isExpanded) {
                // Expanded RecyclerView
                mBinding.greySeperator.setVisibility(View.GONE);
                mBinding.clChild.setVisibility(View.VISIBLE);
                mBinding.blurSeperator.setVisibility(View.VISIBLE);

            } else {
                // Collapse RecyclerView
                mBinding.greySeperator.setVisibility(View.VISIBLE);
                mBinding.clChild.setVisibility(View.GONE);
                mBinding.blurSeperator.setVisibility(View.GONE);
            }

            itemView.setActivated(isExpanded);

            if (isExpanded)
                previousExpandedPosition = position;

            mBinding.clRoot.setOnClickListener(v -> {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            });

        }

        @Override
        protected void viewDetachedFromWindow() {

        }

        @Override
        public void expandCollapseView() {

        }

    }

    public void sortByStars() {
        if (!getData().isEmpty()) {
            previousExpandedPosition = -1;
            mExpandedPosition = -1;
            Collections.sort(getData());
            notifyDataSetChanged();
        }
    }

    public void sortByName() {
        if (!getData().isEmpty()) {
            previousExpandedPosition = -1;
            mExpandedPosition = -1;
            Collections.sort(getData(), new TrendingRepo());
            notifyDataSetChanged();
        }
    }

}