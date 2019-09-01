package com.abhinav.demo.data.local;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.abhinav.demo.data.local.dao.CommentDao;
import com.abhinav.demo.data.local.dao.IssueDao;
import com.abhinav.demo.data.remote.model.Comment;
import com.abhinav.demo.data.remote.model.Issue;

/**
 * File Description
 * <p>
 * Author: Abhinav Pawar
 */
@Database(entities = { Issue.class, Comment.class}, version = 5, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {
    public abstract CommentDao commentDao();
    public abstract IssueDao issueDao();



}