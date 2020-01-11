package com.linkan.githubtrendingrepos.data;

import com.linkan.githubtrendingrepos.data.local.db.DbHelper;
import com.linkan.githubtrendingrepos.data.local.pref.PrefHelper;
import com.linkan.githubtrendingrepos.data.model.api.TrendingResponse;
import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;
import com.linkan.githubtrendingrepos.data.remote.ApiHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AppDataManager implements DataManager {

    private DbHelper dbHelper;
    private ApiHelper apiHelper;
    private PrefHelper prefHelper;

    @Inject
    public AppDataManager(DbHelper dbHelper, ApiHelper apiHelper, PrefHelper prefHelper) {
        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
        this.prefHelper = prefHelper;
    }


    // DataManger Methods
    //----------------------------------------------------------------------------

    @Override
    public boolean isCacheValid() {
        return prefHelper.isCacheValid();
    }

    @Override
    public Observable<List<TrendingResponse>> fetchFromServerOrDB(boolean isCacheValid, String language, String trendingPeriod) {

        return null;

    }


    // Database Methods
    //----------------------------------------------------------------------------

    @Override
    public Observable<Boolean> insertRepo(TrendingRepo trendingRepo) {
        return dbHelper.insertRepo(trendingRepo);
    }

    @Override
    public Observable<Boolean> insertMultipleListRepo(List<TrendingRepo> repoList) {
        return dbHelper.insertMultipleListRepo(repoList);
    }

    @Override
    public Observable<List<TrendingRepo>> loadAllRepo() {
        return dbHelper.loadAllRepo();
    }

    @Override
    public Observable<Boolean> deleteRepo(TrendingRepo trendingRepo) {
        return dbHelper.deleteRepo(trendingRepo);
    }

    @Override
    public Observable<Boolean> deleteRepoRecord() {
        return dbHelper.deleteRepoRecord();
    }


    // App Preference Methods
    //----------------------------------------------------------------------------

    @Override
    public void setCacheTime(Long cacheTime) {
        prefHelper.setCacheTime(cacheTime);
    }

    @Override
    public Long getCacheTime() {
        return prefHelper.getCacheTime();
    }


    // API Methods
    //----------------------------------------------------------------------------

    @Override
    public Single<List<TrendingResponse>> fetchTrendingRepos(String language, String trendingPeriod) {
        return apiHelper.fetchTrendingRepos(language, trendingPeriod);
    }


}
