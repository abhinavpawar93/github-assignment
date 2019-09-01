package com.abhinav.demo.data.remote;


import com.abhinav.demo.data.remote.model.Comment;
import com.abhinav.demo.data.remote.model.Issue;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The APIService interface which will contain the semantics of all the REST calls.
 * Author: Abhinav Pawar
 */
public interface ApiService {
    @GET("repos/firebase/firebase-ios-sdk/issues")
    Single<List<Issue>> getAllIssues(@Query("sort") String sortType);

    @GET("repos/firebase/firebase-ios-sdk/issues/{issueId}/comments")
    Single<List<Comment>> getAllComments(@Path("issueId") long issueId);

}
