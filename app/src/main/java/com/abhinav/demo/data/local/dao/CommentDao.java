package com.abhinav.demo.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abhinav.demo.data.remote.model.Comment;

import java.util.List;


/**
 * File Description
 * <p>
 * Author: Abhinav Pawar
 */
@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    LiveData<List<Comment>> loadComments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveComments(List<Comment> comments);

}
