package com.linkan.githubtrendingrepos.data.model.other;

public class LiveDataEntity {

    public Method methodName;

    public Throwable throwable;

    public LiveDataEntity(Method methodName, Throwable throwable){
        this.methodName = methodName;
        this.throwable = throwable;
    }

}
