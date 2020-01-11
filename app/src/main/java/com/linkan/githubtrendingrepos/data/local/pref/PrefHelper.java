package com.linkan.githubtrendingrepos.data.local.pref;

public interface PrefHelper {

  boolean isCacheValid();

  void setCacheTime(Long cacheTime);

  Long getCacheTime();

}
