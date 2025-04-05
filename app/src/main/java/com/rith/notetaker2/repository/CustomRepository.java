package com.rith.notetaker2.repository;

import io.reactivex.Single;

public class CustomRepository {
    Single<String> single = Single.just("hello");

    Single<String> getSingle = Single.create(emitter->{
        String h = "hello";
        emitter.onSuccess(h);
    });
}
