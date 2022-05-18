package com.ccpp.pgw.android.demo.merchant.activities;

import android.os.Bundle;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.fragments.SDKBuildTypeFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class MainActivity extends BaseActivity {

    private final String TAG = MainActivity.class.getName();

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUnBinder = ButterKnife.bind(this);

        init();
    }

    public void init() {
        initLayout();
    }

    private void initLayout() {

        replaceFragment(SDKBuildTypeFragment.newInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnBinder.unbind();
    }
}
