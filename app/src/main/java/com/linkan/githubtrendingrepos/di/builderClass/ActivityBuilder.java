package com.linkan.githubtrendingrepos.di.builderClass;

import com.linkan.githubtrendingrepos.ui.activity.MainActivity;
import com.linkan.githubtrendingrepos.ui.activity.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    public abstract MainActivity provideMainActivity();

}
