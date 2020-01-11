package com.linkan.githubtrendingrepos.utils;

import androidx.databinding.BindingAdapter;
import android.net.Uri;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;
import com.linkan.githubtrendingrepos.ui.adapter.TrendingRepoAdapter;

import java.util.List;


public class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"repoAdapter"})
    public static void addTrendingRepoList(RecyclerView recyclerView, List<TrendingRepo> timelineFeedList) {

        TrendingRepoAdapter adapter = (TrendingRepoAdapter) recyclerView.getAdapter();

        if (adapter != null) {
                adapter.clearItems();
                adapter.addItems(timelineFeedList);
        }
    }

    @BindingAdapter("bind:imageUrl")
    public static void setImageUrl(SimpleDraweeView draweeView, String imageUrl) {

        Uri uri = Uri.parse(imageUrl == null ? "" : imageUrl);
        draweeView.setImageURI(uri);

    }


    @BindingAdapter({"pullToRefresh", "enableRefreshing"})
    public static void bindSwipeRefreshListener(SwipeRefreshLayout pullToRefresh,
                                                SwipeRefreshLayout.OnRefreshListener refreshListener,
                                                boolean refreshLoader) {
        pullToRefresh.setOnRefreshListener(refreshListener);

        pullToRefresh.setRefreshing(refreshLoader);

        pullToRefresh.setColorSchemeResources(android.R.color.black,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }



}