package com.abhinav.demo.data.remote.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhinav.demo.data.local.dao.IssueDao;
import com.abhinav.demo.data.remote.ApiService;
import com.abhinav.demo.data.remote.NetworkBoundResource;
import com.abhinav.demo.data.remote.Resource;
import com.abhinav.demo.data.remote.model.Comment;
import com.abhinav.demo.data.remote.model.Issue;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class IssueRepository {
    private ApiService apiService;
    private IssueDao issueDao;


    @Inject
    public IssueRepository(ApiService apiService, IssueDao issueDao) {
        this.apiService = apiService;
        this.issueDao = issueDao;
    }


    public LiveData<Resource<List<Comment>>> getAllComments(long issueId) {
        return new LiveDataHelper<List<Comment>>() {
            @NonNull
            @Override
            protected Single<List<Comment>> createCall() {
                return apiService.getAllComments(issueId);
            }
        }.getAsLiveData();

    }


    public MutableLiveData<Resource<List<Issue>>> getAllIssues() {
        return new NetworkBoundResource<List<Issue>, List<Issue>>() {
            @Override
            protected void saveCallResult(List<Issue> item) {
                issueDao.saveIssue(item);
            }

            @NonNull
            @Override
            protected LiveData<List<Issue>> loadFromDb() {
                return issueDao.loadIssues();
            }

            @NonNull
            @Override
            protected Single<List<Issue>> createCall() {
                return apiService.getAllIssues("updated");
            }
        }.getAsLiveData();
    }

    public LiveData<List<Issue>> getAllIssueFromDb() {
        return issueDao.loadIssues();
    }
}
