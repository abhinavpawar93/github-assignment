package com.abhinav.demo.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abhinav.demo.data.remote.model.Issue;

import java.util.List;

/**
 * File Description
 * <p>
 * Author: Abhinav Pawar
 */
@Dao
public interface IssueDao {
    @Query("SELECT * FROM issue")
    LiveData<List<Issue>> loadIssues();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveIssue(List<Issue> issues);

    @Query("SELECT COUNT(*) FROM issue")
    LiveData< Integer> getCount();


}
