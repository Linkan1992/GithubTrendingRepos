package com.linkan.githubtrendingrepos.data.remote;

import com.linkan.githubtrendingrepos.data.model.api.TrendingResponse;
import com.linkan.githubtrendingrepos.data.remote.ApiEndPoints;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

  @GET(ApiEndPoints.GetTrendingRepos)
  Single<List<TrendingResponse>> fetchTrendingRepos(@Query("language") String language,
                                                    @Query("since") String trendingPeriod);

}
