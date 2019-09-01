package com.abhinav.demo.di.module;

import android.app.Application;

import androidx.room.Room;

import com.abhinav.demo.data.local.ArticleDatabase;
import com.abhinav.demo.data.local.dao.CommentDao;
import com.abhinav.demo.data.local.dao.IssueDao;
import com.abhinav.demo.data.remote.ApiConstants;
import com.abhinav.demo.data.remote.ApiService;
import com.abhinav.demo.data.remote.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The application module which provides app wide instances of various components
 * Author: Abhinav Pawar
 */
@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.addInterceptor(new RequestInterceptor());
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    ApiService provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    ArticleDatabase provideArticleDatabase(Application application) {
        return Room.databaseBuilder(application, ArticleDatabase.class, "articles.db").build();
    }

    @Provides
    @Singleton
    IssueDao provideIssueDao(ArticleDatabase articleDatabase) {
        return articleDatabase.issueDao();
    }

    @Provides
    @Singleton
    CommentDao provideCommentDao(ArticleDatabase articleDatabase) {
        return articleDatabase.commentDao();
    }

}
