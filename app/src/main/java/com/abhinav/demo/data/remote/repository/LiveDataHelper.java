package com.abhinav.demo.data.remote.repository;


import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhinav.demo.DemoApp;
import com.abhinav.demo.R;
import com.abhinav.demo.data.remote.Resource;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * This class act as the decider to cache the response/ fetch from the service always
 * Author: Abhinav Pawar
 */
public abstract class LiveDataHelper<T> {

    private final MediatorLiveData<Resource<T>> result = new MediatorLiveData<>();

    @MainThread
    protected LiveDataHelper() {
        result.setValue(Resource.loading(null));

        fetchFromNetwork();

    }

    private void fetchFromNetwork() {

        createCall().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableSingleObserver<T>() {
            @Override
            public void onSuccess(T v) {
                result.setValue(Resource.success(v));
            }

            @Override
            public void onError(Throwable e) {
                result.setValue(Resource.error(getCustomErrorMessage(e), null));

            }
        });
    }

    private String getCustomErrorMessage(Throwable error) {

        if (error instanceof SocketTimeoutException) {
            return DemoApp.getAppContext().getString(R.string.requestTimeOutError);
        } else if (error instanceof MalformedJsonException) {
            return DemoApp.getAppContext().getString(R.string.responseMalformedJson);
        } else if (error instanceof IOException) {
            return DemoApp.getAppContext().getString(R.string.networkError);
        } else if (error instanceof HttpException) {
            return (((HttpException) error).response().message());
        } else {
            return DemoApp.getAppContext().getString(R.string.unknownError);
        }

    }


    @NonNull
    @MainThread
    protected abstract Single<T> createCall();

    public final MutableLiveData<Resource<T>> getAsLiveData() {
        return result;
    }
}
