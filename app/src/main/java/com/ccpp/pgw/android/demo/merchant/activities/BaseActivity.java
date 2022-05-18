package com.ccpp.pgw.android.demo.merchant.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ccpp.pgw.android.demo.merchant.R;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class BaseActivity extends AppCompatActivity {

    public void replaceFragment(Fragment fragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }
}
