package com.linkan.githubtrendingrepos.base;

import com.linkan.githubtrendingrepos.data.model.other.Status;

public class State<T> {

    public ResourceType<T> success(T data) {
        return new ResourceType<T>(Status.SUCCESS, data);
    }

    public ResourceType<T> error(String errorMessage) {
        return new ResourceType<T>(Status.ERROR, errorMessage);
    }

    public ResourceType<T> loading() {
        return new ResourceType<T>(Status.LOADING);
    }

    public ResourceType<T> refresh() {
        return new ResourceType<T>(Status.REFRESH);
    }

}
