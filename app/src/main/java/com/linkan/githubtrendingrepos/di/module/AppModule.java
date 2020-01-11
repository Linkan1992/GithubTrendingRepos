package com.linkan.githubtrendingrepos.di.module;

import android.app.Application;
import androidx.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.linkan.githubtrendingrepos.R;
import com.linkan.githubtrendingrepos.RxScheduler.AppSchedulerProvider;
import com.linkan.githubtrendingrepos.RxScheduler.SchedulerProvider;
import com.linkan.githubtrendingrepos.data.AppDataManager;
import com.linkan.githubtrendingrepos.data.DataManager;
import com.linkan.githubtrendingrepos.data.local.dao.AppDatabase;
import com.linkan.githubtrendingrepos.data.local.db.AppDbHelper;
import com.linkan.githubtrendingrepos.data.local.db.DbHelper;
import com.linkan.githubtrendingrepos.data.local.pref.AppPreferenceHelper;
import com.linkan.githubtrendingrepos.data.local.pref.PrefHelper;
import com.linkan.githubtrendingrepos.data.remote.APIInterface;
import com.linkan.githubtrendingrepos.data.remote.ApiEndPoints;
import com.linkan.githubtrendingrepos.data.remote.ApiHelper;
import com.linkan.githubtrendingrepos.data.remote.AppApiHelper;
import com.linkan.githubtrendingrepos.di.annotation.DatabaseInfo;
import com.linkan.githubtrendingrepos.di.annotation.PreferenceInfo;
import com.linkan.githubtrendingrepos.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideApplicationContext(Application application) {
        return application;
    }

//    @Provides
//    @Singleton
//    DataManager provideDataManager(AppDataManager appDataManager) {
//        return appDataManager;
//    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    PrefHelper providePrefHelper(AppPreferenceHelper appPrefHelper) {
        return appPrefHelper;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    SchedulerProvider provideScheduleProvider() {
        return new AppSchedulerProvider();
    }


    @Provides
    @Singleton
    APIInterface provideAPIService(Retrofit retrofit) {
        return retrofit.create(APIInterface.class);
    }


    @Provides
    @Singleton
    Retrofit getRetrofit(OkHttpClient okHttpClient, Gson gson) {

        return new Retrofit.Builder()
                .baseUrl(ApiEndPoints.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    Gson provideGSONObject() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Interceptor headerInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Interceptor getHeaderInterceptor(AppPreferenceHelper appPrefHelper) {
        return chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

}
