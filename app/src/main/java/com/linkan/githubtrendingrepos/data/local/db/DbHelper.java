package com.linkan.githubtrendingrepos.data.local.db;

import com.linkan.githubtrendingrepos.data.model.api.TrendingResponse;
import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {

  Observable<Boolean> insertRepo(TrendingRepo trendingRepo);

  Observable<Boolean> insertMultipleListRepo(List<TrendingRepo> repoList);

  Observable<List<TrendingRepo>> loadAllRepo();

  Observable<Boolean> deleteRepo(TrendingRepo trendingRepo);

  Observable<Boolean> deleteRepoRecord();

}
