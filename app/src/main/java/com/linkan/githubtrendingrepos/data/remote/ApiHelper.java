package com.linkan.githubtrendingrepos.data.remote;


import com.linkan.githubtrendingrepos.data.model.api.TrendingResponse;

import java.util.List;

import io.reactivex.Single;

public interface ApiHelper {

  Single<List<TrendingResponse>> fetchTrendingRepos(String language, String trendingPeriod);

}
