package com.linkan.githubtrendingrepos.base;

import androidx.databinding.ObservableBoolean;

public class EmptyErrorViewModel {

    private final ObservableBoolean isVisible = new ObservableBoolean(false);

    private RetryNavigator onRetryListener;

    public EmptyErrorViewModel(RetryNavigator onRetryListener){
        this.onRetryListener = onRetryListener;
    }

    public void onRetry(){
        /**
         * On click of button show grey background and hide the
         * layout
         */
        setIsVisible(false);
        onRetryListener.onRetryClick();
    }

    public void setIsVisible(boolean value){
        this.isVisible.set(value);
    }

    public ObservableBoolean getIsVisible() {
        return isVisible;
    }

}
