package com.abhinav.demo.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.abhinav.demo.background.workmanager.DailySyncWorker;
import com.abhinav.demo.data.remote.Resource;
import com.abhinav.demo.data.remote.model.Comment;
import com.abhinav.demo.data.remote.model.Issue;
import com.abhinav.demo.data.remote.repository.IssueRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class GithubViewModel extends ViewModel {
    private static final String WORKER_TAG = "DAILY_SYNC_MY_APP";
    private MediatorLiveData<Resource<List<Issue>>> issuesLiveData;
    private IssueRepository issueRepository;
    private LiveData<Resource<List<Comment>>> commentLiveData;
    private MediatorLiveData<Integer> issueCountFromDb;

    @Inject
    public GithubViewModel(IssueRepository issueRepository) {
        issueCountFromDb = new MediatorLiveData<>();
        issuesLiveData = new MediatorLiveData<>();
        this.issueRepository = issueRepository;
        LiveData<List<Issue>> allIssueFromDb = issueRepository.getAllIssueFromDb();
        issuesLiveData.addSource(allIssueFromDb, new Observer<List<Issue>>() {
            @Override
            public void onChanged(List<Issue> issues) {
                if (issues.size() > 1) {
                    issuesLiveData.setValue(Resource.success(issues));
                } else {
                    startSyncProcess();
                    issuesLiveData.addSource(issueRepository.getAllIssues(), new Observer<Resource<List<Issue>>>() {
                        @Override
                        public void onChanged(Resource<List<Issue>> listResource) {
                            issuesLiveData.setValue(listResource);
                        }
                    });
                }
                issuesLiveData.removeSource(allIssueFromDb);
            }
        });
    }


    public void getAllCommentsOfAIssue(long issueNumber) {
        commentLiveData = issueRepository.getAllComments(issueNumber);
    }


    public LiveData<Resource<List<Issue>>> getIssuesLiveData() {
        return issuesLiveData;
    }

    public LiveData<Resource<List<Comment>>> getCommentLiveData() {
        return commentLiveData;
    }


    public void startSyncProcess() {
        Constraints networkContraint = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest.Builder myWorkBuilder =
                new PeriodicWorkRequest.Builder(DailySyncWorker.class, 24, TimeUnit.HOURS);
        PeriodicWorkRequest myWork = myWorkBuilder.setConstraints(networkContraint).build();
        WorkManager.getInstance()
                .enqueueUniquePeriodicWork(WORKER_TAG, ExistingPeriodicWorkPolicy.KEEP, myWork);
    }
}
