package com.abhinav.demo.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhinav.demo.data.remote.model.Comment;
import com.abhinav.demo.databinding.ItemCommentBinding;
import com.abhinav.demo.view.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the comment list recyclerview adapter
 * Author: Abhinav Pawar
 */
public class CommentListAdapter extends BaseAdapter<CommentListAdapter.CommentViewHolder, Comment> {

    private List<Comment> commentList;

    public CommentListAdapter() {
        commentList = new ArrayList<>();
    }


    @Override
    public void setData(List<Comment> entities) {
        this.commentList = entities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return CommentViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder viewHolder, int i) {
        viewHolder.onBind(commentList.get(i));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    static class CommentViewHolder extends RecyclerView.ViewHolder {

        private static CommentViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            ItemCommentBinding itemCommentBinding = ItemCommentBinding.inflate(inflater, parent, false);
            return new CommentViewHolder(itemCommentBinding);
        }

        final ItemCommentBinding binding;

        private CommentViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void onBind(Comment comment) {
            binding.setComment(comment);
            binding.executePendingBindings();
        }
    }
}
