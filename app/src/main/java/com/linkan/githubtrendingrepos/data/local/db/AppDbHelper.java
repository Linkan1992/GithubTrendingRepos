package com.linkan.githubtrendingrepos.data.local.db;

import com.linkan.githubtrendingrepos.data.local.dao.AppDatabase;
import com.linkan.githubtrendingrepos.data.model.api.TrendingResponse;
import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;


public class AppDbHelper implements DbHelper {

  private AppDatabase appDatabase;

  @Inject
  public AppDbHelper(AppDatabase appDatabase) {
    this.appDatabase = appDatabase;
  }

  @Override
  public Observable<Boolean> insertRepo(TrendingRepo trendingRepo) {
    return Observable.fromCallable(() -> {
      appDatabase.repoDao().insertRepo(trendingRepo);
      return true;
    });
  }

  @Override
  public Observable<Boolean> insertMultipleListRepo(List<TrendingRepo> repoList) {
    return Observable.fromCallable(() -> {
      appDatabase.repoDao().insertMultipleListRepo(repoList);
      return true;
    });
  }


  @Override
  public Observable<List<TrendingRepo>> loadAllRepo() {
    return Observable.fromCallable(() -> appDatabase.repoDao().loadAllRepo());
  }

  @Override
  public Observable<Boolean> deleteRepo(TrendingRepo trendingRepo) {
    return Observable.fromCallable(() -> {
      appDatabase.repoDao().deleteRepo(trendingRepo);
      return true;
    });
  }

  @Override
  public Observable<Boolean> deleteRepoRecord() {
    return Observable.fromCallable(() -> {
      appDatabase.repoDao().deleteRepoRecord();
      return true;
    });
  }


}
