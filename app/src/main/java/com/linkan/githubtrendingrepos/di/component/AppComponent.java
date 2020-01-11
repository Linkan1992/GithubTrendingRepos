package com.linkan.githubtrendingrepos.di.component;

import android.app.Application;

import com.linkan.githubtrendingrepos.BaseApplication;
import com.linkan.githubtrendingrepos.di.builderClass.ActivityBuilder;
import com.linkan.githubtrendingrepos.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@dagger.Component(modules = {AndroidSupportInjectionModule.class, AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(BaseApplication app);

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}