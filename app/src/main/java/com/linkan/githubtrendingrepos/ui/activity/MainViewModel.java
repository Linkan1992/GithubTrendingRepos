package com.linkan.githubtrendingrepos.ui.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import com.linkan.githubtrendingrepos.RxScheduler.SchedulerProvider;
import com.linkan.githubtrendingrepos.base.BaseViewModel;
import com.linkan.githubtrendingrepos.base.ResourceType;
import com.linkan.githubtrendingrepos.base.State;
import com.linkan.githubtrendingrepos.data.local.db.DbHelper;
import com.linkan.githubtrendingrepos.data.local.pref.PrefHelper;
import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;
import com.linkan.githubtrendingrepos.data.remote.ApiHelper;
import com.linkan.githubtrendingrepos.utils.UtilFunction;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends BaseViewModel {

    private final DbHelper dbHelper;
    private final ApiHelper apiHelper;
    private final PrefHelper prefHelper;


    private MediatorLiveData<ResourceType<List<TrendingRepo>>> mTrendingRepoLiveData = new MediatorLiveData<>();

    private final ObservableList<TrendingRepo> repoObservableList = new ObservableArrayList<>();

    public ObservableBoolean refreshLoader = new ObservableBoolean(false);

    public MainViewModel(DbHelper dbHelper, ApiHelper apiHelper, PrefHelper prefHelper, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);

        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
        this.prefHelper = prefHelper;
        fetchFromCacheOrServer(false);

    }


    public void fetchFromCacheOrServer(boolean isPullToRefresh) {
        /*if (this.prefHelper.isCacheValid())
            this.mTrendingRepoLiveData = fetchRepoFromCachedDB(isPullToRefresh);
        else
            this.mTrendingRepoLiveData = fetchRepoFromServer(isPullToRefresh);*/

        fetchLiveDataFromServerOrCache(isPullToRefresh);

    }


    public void fetchLiveDataFromServerOrCache(final boolean isPullToRefresh) {

        Flowable<List<TrendingRepo>> flow = this.prefHelper.isCacheValid() ? fetchFlowableFromCachedDB(isPullToRefresh) : fetchFlowableFromServer(isPullToRefresh);

        LiveData<List<TrendingRepo>> source = LiveDataReactiveStreams.fromPublisher(flow);

        State<List<TrendingRepo>> state = new State<>();

        mTrendingRepoLiveData.setValue(isPullToRefresh ? state.refresh() : state.loading());

        mTrendingRepoLiveData.addSource(source, new Observer<List<TrendingRepo>>() {
            @Override
            public void onChanged(List<TrendingRepo> repoList) {
                if (repoList != null && !repoList.isEmpty()) {
                    mTrendingRepoLiveData.setValue(state.success(repoList));
                } else{
                    mTrendingRepoLiveData.setValue(state.error("No Data Found"));
                }
                mTrendingRepoLiveData.removeSource(source);
            }
        });

    }


    public Flowable<List<TrendingRepo>> fetchFlowableFromServer1(final boolean isPullToRefresh) {

        return this.apiHelper
                .fetchTrendingRepos(null, "daily")
                .doOnSubscribe(__ -> {
                    if (isPullToRefresh) {
                        setRefreshLoader(true);
                    } else {
                        setIsLoading(true);
                    }
                })
                .map(UtilFunction::getTrendingRepoList)
                .doOnSuccess(this::clearCachedRepo)
                .doAfterSuccess(repoList -> this.prefHelper.setCacheTime(System.currentTimeMillis()))
                .onErrorReturn(error -> new ArrayList<>())
                .doFinally(() -> {
                    setRefreshLoader(false);
                    setIsLoading(false);
                })
                .subscribeOn(getSchedulerProvider().io())
                .toFlowable();

    }


    private Flowable<List<TrendingRepo>> fetchFlowableFromCachedDB1(boolean isPullToRefresh) {

        return this.dbHelper
                .loadAllRepo()
                .doOnSubscribe(__ -> {
                    if (isPullToRefresh)
                        setRefreshLoader(true);
                })
                .doOnError(error -> getErrorLiveData().setValue(error))
                .doFinally(() -> setRefreshLoader(false))
                .subscribeOn(getSchedulerProvider().io())
                .toFlowable(BackpressureStrategy.LATEST);

    }


    public Flowable<List<TrendingRepo>> fetchFlowableFromServer(final boolean isPullToRefresh) {

        return this.apiHelper
                .fetchTrendingRepos(null, "daily")
                .map(UtilFunction::getTrendingRepoList)
                .doOnSuccess(this::clearCachedRepo)
                .doAfterSuccess(repoList -> this.prefHelper.setCacheTime(System.currentTimeMillis()))
                .onErrorReturn(error -> new ArrayList<>())
             //   .doOnError(error -> mTrendingRepoLiveData.setValue(new State<List<TrendingRepo>>().error("No Data Found")))
                .subscribeOn(getSchedulerProvider().io())
                .toFlowable();

    }


    private Flowable<List<TrendingRepo>> fetchFlowableFromCachedDB(boolean isPullToRefresh) {

        return this.dbHelper
                .loadAllRepo()
                .onErrorReturn(error -> new ArrayList<>())
              //  .doOnError(error -> mTrendingRepoLiveData.setValue(new State<List<TrendingRepo>>().error("No Data Found")))
                .subscribeOn(getSchedulerProvider().io())
                .toFlowable(BackpressureStrategy.LATEST);

    }


    private void syncDbCache(List<TrendingRepo> repoList) {
        getCompositeDisposable().add(
                this.dbHelper
                        .insertMultipleListRepo(repoList)
                        .compose(provideScheduler())
                        .subscribe(
                                response -> {

                                },
                                error -> {
                                    mTrendingRepoLiveData.setValue(new State<List<TrendingRepo>>().error("No Data Found"));
                                }
                        ));
    }


    public void clearCachedRepo(List<TrendingRepo> repoList) {

        getCompositeDisposable().add(
                this.dbHelper
                        .deleteRepoRecord()
                        .compose(provideScheduler())
                        .subscribe(
                                response -> {
                                    syncDbCache(repoList);
                                },
                                error -> {
                                    mTrendingRepoLiveData.setValue(new State<List<TrendingRepo>>().error("No Data Found"));
                                }
                        ));
    }


    // pull to refresh
    public SwipeRefreshLayout.OnRefreshListener getPullToRefreshListener() {

        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFromCacheOrServer(true);
            }
        };
    }


    public void setRefreshLoader(boolean isLoading) {
        refreshLoader.set(isLoading);
    }


    public void setRepoObservableList(List<TrendingRepo> repoList) {
        this.repoObservableList.clear();
        this.repoObservableList.addAll(repoList);
    }

    public LiveData<ResourceType<List<TrendingRepo>>> getTrendingRepoLiveData() {
        return mTrendingRepoLiveData;
    }

    public ObservableList<TrendingRepo> getRepoObservableList() {
        return repoObservableList;
    }


}
