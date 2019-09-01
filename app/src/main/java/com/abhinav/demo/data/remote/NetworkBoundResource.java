package com.abhinav.demo.data.remote;


import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhinav.demo.DemoApp;
import com.abhinav.demo.R;
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
public abstract class NetworkBoundResource<T, V> {

    private final MediatorLiveData<Resource<T>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource() {
        result.setValue(Resource.loading(null));

        // Always load the data from DB intially so that we have
        LiveData<T> dbSource = loadFromDb();

        // Fetch the data from network and add it to the resource
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch()) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        });
    }

    /**
     * This method fetches the data from remoted service and save it to local db
     *
     * @param dbSource - Database source
     */
    private void fetchFromNetwork(final LiveData<T> dbSource) {
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));

        createCall().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableSingleObserver<V>() {
            @Override
            public void onSuccess(V v) {
                result.removeSource(dbSource);
                saveResultAndReInit(v);
            }

            @Override
            public void onError(Throwable e) {
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> result.setValue(Resource.error(getCustomErrorMessage(e), newData)));

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

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private void saveResultAndReInit(V response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(V item);

    @MainThread
    private boolean shouldFetch() {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<T> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Single<V> createCall();

    public final MutableLiveData<Resource<T>> getAsLiveData() {
        return result;
    }
}
