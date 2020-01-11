package com.linkan.githubtrendingrepos.data.model.other;

public enum Status {
    SUCCESS, ERROR, LOADING, REFRESH, COMPLETED;


    public boolean isLoading(){
        return this == LOADING;
    }
}
