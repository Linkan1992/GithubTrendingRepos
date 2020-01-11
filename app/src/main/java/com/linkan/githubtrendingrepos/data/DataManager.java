package com.linkan.githubtrendingrepos.data;

import com.linkan.githubtrendingrepos.data.local.db.DbHelper;
import com.linkan.githubtrendingrepos.data.local.pref.PrefHelper;
import com.linkan.githubtrendingrepos.data.model.api.TrendingResponse;
import com.linkan.githubtrendingrepos.data.remote.ApiHelper;

import java.util.List;

import io.reactivex.Observable;

public interface DataManager extends DbHelper, ApiHelper, PrefHelper {


    Observable<List<TrendingResponse>> fetchFromServerOrDB(boolean isCacheValid, String language, String trendingPeriod);


}
