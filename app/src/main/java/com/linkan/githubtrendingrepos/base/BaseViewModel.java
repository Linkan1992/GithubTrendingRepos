package com.linkan.githubtrendingrepos.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableBoolean;
import androidx.annotation.Nullable;

import com.linkan.githubtrendingrepos.RxScheduler.SchedulerProvider;
import com.linkan.githubtrendingrepos.data.DataManager;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {


    private final ObservableBoolean loading = new ObservableBoolean(false);

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;


    public final MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();


    public BaseViewModel(SchedulerProvider scheduleProvider) {

        this.mSchedulerProvider = scheduleProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }


    protected <T> ObservableTransformer<T, T> provideScheduler() {
        return upstream -> upstream
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui());
    }


    public void setIsLoading(boolean isLoading) {
        loading.set(isLoading);
    }

    public ObservableBoolean getLoading() {
        return loading;
    }


    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

}
