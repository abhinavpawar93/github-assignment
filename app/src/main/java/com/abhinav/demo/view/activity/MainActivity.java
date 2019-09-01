package com.abhinav.demo.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.abhinav.demo.R;
import com.abhinav.demo.databinding.ActivityMainBinding;
import com.abhinav.demo.utils.FragmentUtils;
import com.abhinav.demo.view.base.BaseActivity;
import com.abhinav.demo.view.fragment.IssueListFragment;

import static com.abhinav.demo.utils.FragmentUtils.TRANSITION_NONE;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentUtils.replaceFragment(this, IssueListFragment.newInstance(), R.id.fragContainer, false, TRANSITION_NONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
