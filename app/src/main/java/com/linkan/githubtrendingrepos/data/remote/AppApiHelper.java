package com.linkan.githubtrendingrepos.data.remote;

import com.linkan.githubtrendingrepos.data.model.api.TrendingResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AppApiHelper implements ApiHelper {

  private APIInterface apiInterface;

  @Inject
  public AppApiHelper(APIInterface apiInterface){
    this.apiInterface = apiInterface;
  }


  @Override
  public Single<List<TrendingResponse>> fetchTrendingRepos(String language, String trendingPeriod) {
    return apiInterface.fetchTrendingRepos(language, trendingPeriod);
  }


}
