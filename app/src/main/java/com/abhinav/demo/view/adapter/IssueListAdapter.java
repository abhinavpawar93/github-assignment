package com.abhinav.demo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhinav.demo.data.remote.model.Issue;
import com.abhinav.demo.databinding.ItemIssueBinding;
import com.abhinav.demo.view.base.BaseAdapter;
import com.abhinav.demo.view.callbacks.IssueListCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Issue list recyclerview adapter
 * Author: Abhinav Pawar
 */
public class IssueListAdapter extends BaseAdapter<IssueListAdapter.IssueViewHolder, Issue> {

    private List<Issue> issueList;
    private IssueListCallback issueListCallback;

    public IssueListAdapter(IssueListCallback issueListCallback) {
        this.issueListCallback = issueListCallback;
        issueList = new ArrayList<>();
    }


    @Override
    public void setData(List<Issue> entities) {
        this.issueList = entities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return IssueViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, issueListCallback
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder viewHolder, int i) {
        viewHolder.onBind(issueList.get(i));
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }


    static class IssueViewHolder extends RecyclerView.ViewHolder {

        private static IssueViewHolder create(LayoutInflater inflater, ViewGroup parent, IssueListCallback issueListCallback) {
            ItemIssueBinding itemIssueBinding = ItemIssueBinding.inflate(inflater, parent, false);
            return new IssueViewHolder(itemIssueBinding, issueListCallback);
        }

        final ItemIssueBinding binding;

        private IssueViewHolder(ItemIssueBinding binding, IssueListCallback issueListCallback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    issueListCallback.onIssueClicked(binding.getIssue().number);
                }
            });
        }

        private void onBind(Issue issue) {
            if (issue.body.length() > 140)
                issue.body = issue.body.substring(0, 140).concat("...");
            binding.setIssue(issue);
            binding.executePendingBindings();
        }
    }
}
