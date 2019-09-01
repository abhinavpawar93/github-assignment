package com.abhinav.demo.view.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abhinav.demo.R;
import com.abhinav.demo.data.remote.Resource;
import com.abhinav.demo.data.remote.Status;
import com.abhinav.demo.data.remote.model.Issue;
import com.abhinav.demo.databinding.FragmentIssueListBinding;
import com.abhinav.demo.utils.FragmentUtils;
import com.abhinav.demo.view.adapter.IssueListAdapter;
import com.abhinav.demo.view.base.BaseFragment;
import com.abhinav.demo.view.callbacks.IssueListCallback;
import com.abhinav.demo.viewmodel.GithubViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class IssueListFragment extends BaseFragment<GithubViewModel, FragmentIssueListBinding> implements IssueListCallback {

    public static IssueListFragment newInstance() {

        Bundle args = new Bundle();

        IssueListFragment fragment = new IssueListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBinding.rvIssues.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.rvIssues.setAdapter(new IssueListAdapter(this));

        viewModel.getIssuesLiveData().observe(this, new Observer<Resource<List<Issue>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Issue>> listResource) {
                if (listResource.status == Status.SUCCESS || listResource.data != null) {
                    dataBinding.setIssueData(listResource);

                } else if (listResource.status == Status.ERROR) {
                    Snackbar.make(view, "Error retrieving issues", Snackbar.LENGTH_SHORT);
                } else if (listResource.status == Status.LOADING) {
                    Snackbar.make(view, "Loading...", Snackbar.LENGTH_SHORT);

                }
            }

        });
    }

    @Override
    protected Class<GithubViewModel> getViewModel() {
        return GithubViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_issue_list;
    }

    @Override
    public void onIssueClicked(Integer issueNumber) {
        if (getActivity() != null) {
            CommentListFragment detailFragment = CommentListFragment.newInstance(issueNumber);
            FragmentUtils.replaceFragment((AppCompatActivity) getActivity(), detailFragment, R.id.fragContainer, true, FragmentUtils.TRANSITION_SLIDE_LEFT_RIGHT);
        }
    }
}
