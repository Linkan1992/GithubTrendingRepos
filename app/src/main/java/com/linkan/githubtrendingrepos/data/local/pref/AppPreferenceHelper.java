package com.linkan.githubtrendingrepos.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;


import com.linkan.githubtrendingrepos.di.annotation.PreferenceInfo;
import com.linkan.githubtrendingrepos.utils.AppConstants;

import javax.inject.Inject;

public class AppPreferenceHelper implements PrefHelper {

  private static final String DEFAULT_EMPTY_STRING = "";
  private final SharedPreferences mPrefs;


  @Inject
  public AppPreferenceHelper(Context context, @PreferenceInfo String Pref_Name) {
    mPrefs = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
  }


  @Override
  public boolean isCacheValid() {
    return ((System.currentTimeMillis() - getCacheTime()) / 1000) < 220
            && ((System.currentTimeMillis() -  getCacheTime()) / 1000) > 0;
  }

  @Override
  public void setCacheTime(Long cacheTime) {
    mPrefs.edit().putLong(AppConstants.Extras.CACHE_TIME, cacheTime).apply();
  }


  // time is stored in millis
  @Override
  public Long getCacheTime() {
    return mPrefs.getLong(AppConstants.Extras.CACHE_TIME, 0);
  }

}
