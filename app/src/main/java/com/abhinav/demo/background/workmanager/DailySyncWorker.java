package com.abhinav.demo.background.workmanager;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.abhinav.demo.data.local.ArticleDatabase;
import com.abhinav.demo.data.local.dao.IssueDao;
import com.abhinav.demo.data.remote.ApiConstants;
import com.abhinav.demo.data.remote.ApiService;
import com.abhinav.demo.data.remote.RequestInterceptor;
import com.abhinav.demo.data.remote.model.Issue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DailySyncWorker extends Worker {

    public DailySyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build();

        apiService = retrofit.create(ApiService.class);
        issueDao = provideArticleDatabase((Application) context).issueDao();


    }

    ArticleDatabase provideArticleDatabase(Application application) {
        return Room.databaseBuilder(application, ArticleDatabase.class, "articles.db").build();
    }

    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.addInterceptor(new RequestInterceptor());
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return okHttpClient.build();
    }

    private ApiService apiService;

    private IssueDao issueDao;


    @NonNull
    @Override
    public Result doWork() {


        Single<List<Issue>> updated = apiService.getAllIssues("updated");
        updated.subscribe(new DisposableSingleObserver<List<Issue>>() {
            @Override
            public void onSuccess(List<Issue> v) {
                issueDao.saveIssue(v);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        Log.d(DailySyncWorker.class.getName(), "Hogya");
        return Result.success();
    }
}
