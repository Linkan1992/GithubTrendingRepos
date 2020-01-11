package com.linkan.githubtrendingrepos.ui.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.linkan.githubtrendingrepos.ui.adapter.TrendingRepoAdapter;
import java.util.ArrayList;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    LinearLayoutManager provideLinearLayoutManager(MainActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    TrendingRepoAdapter provideLRepoAdapter() {
        return new TrendingRepoAdapter(new ArrayList<>());
    }

}
