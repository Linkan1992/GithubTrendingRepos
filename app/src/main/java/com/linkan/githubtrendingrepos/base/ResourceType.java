package com.linkan.githubtrendingrepos.base;

import com.linkan.githubtrendingrepos.data.model.other.Status;

public class ResourceType<T> {

    public Status status;
    public String errorMessage;
    public T data;

    public ResourceType(Status status){
        this.status = status;
    }

    public ResourceType(Status status, T data){
        this.status = status;
        this.data = data;
    }

    public ResourceType(Status status, String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }

}
