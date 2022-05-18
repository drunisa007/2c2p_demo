package com.ccpp.pgw.android.demo.merchant.fragments;

import android.support.v4.app.Fragment;

import com.ccpp.pgw.android.demo.merchant.activities.MainActivity;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class BaseFragment extends Fragment {

    public void replaceFragment(Fragment fragment) {

        if(getActivity() != null) {
            ((MainActivity) getActivity()).replaceFragment(fragment);
        }
    }

    protected void updateToolBarTitle(String title) {

        if(getActivity() != null) {
            getActivity().setTitle(title);
        }
    }
}
