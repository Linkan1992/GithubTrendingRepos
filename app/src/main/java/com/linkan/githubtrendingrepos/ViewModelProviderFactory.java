package com.linkan.githubtrendingrepos;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.linkan.githubtrendingrepos.RxScheduler.SchedulerProvider;
import com.linkan.githubtrendingrepos.data.DataManager;
import com.linkan.githubtrendingrepos.data.local.db.DbHelper;
import com.linkan.githubtrendingrepos.data.local.pref.PrefHelper;
import com.linkan.githubtrendingrepos.data.remote.ApiHelper;
import com.linkan.githubtrendingrepos.ui.activity.MainViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Linkan on 19/10/19.
 * <p>
 * A provider factory that persists ViewModels {@link ViewModel}.
 * Used if the view model has a parameterized constructor.
 */

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

  //  private final DataManager dataManager;
    private final DbHelper dbHelper;
    private final ApiHelper apiHelper;
    private final PrefHelper prefHelper;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactory(DbHelper dbHelper, ApiHelper apiHelper, PrefHelper prefHelper, SchedulerProvider schedulerProvider) {
       // this.dataManager = dataManager;
        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
        this.prefHelper = prefHelper;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //noinspection unchecked
            return (T) new MainViewModel(dbHelper, apiHelper, prefHelper, schedulerProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}