package com.abhinav.demo.view.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.abhinav.demo.R;
import com.abhinav.demo.data.remote.Resource;
import com.abhinav.demo.data.remote.Status;
import com.abhinav.demo.data.remote.model.Comment;
import com.abhinav.demo.databinding.FragmentCommentListBinding;
import com.abhinav.demo.view.adapter.CommentListAdapter;
import com.abhinav.demo.view.base.BaseFragment;
import com.abhinav.demo.viewmodel.GithubViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CommentListFragment extends BaseFragment<GithubViewModel, FragmentCommentListBinding> {
    private final static String ISSUE_NUMBER = "issueNumber";

    public static CommentListFragment newInstance(Integer issueNumber) {

        Bundle args = new Bundle();
        args.putInt(ISSUE_NUMBER, issueNumber);
        CommentListFragment fragment = new CommentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<GithubViewModel> getViewModel() {
        return GithubViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBinding.rvComments.setAdapter(new CommentListAdapter());
        if (getArguments() != null && getArguments().getInt(ISSUE_NUMBER, 0) != 0) {
            Integer issueNumber = getArguments().getInt(ISSUE_NUMBER);
            viewModel.getAllCommentsOfAIssue(issueNumber);
        }
        viewModel.getCommentLiveData().observe(this, new Observer<Resource<List<Comment>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Comment>> listResource) {
                if (listResource.status == Status.SUCCESS) {
                    dataBinding.setCommentData(listResource);

                } else if (listResource.status == Status.ERROR) {
                    Snackbar.make(view, "Error retrieving issues", Snackbar.LENGTH_SHORT);
                } else if (listResource.status == Status.LOADING) {

                }
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_comment_list;
    }
}
