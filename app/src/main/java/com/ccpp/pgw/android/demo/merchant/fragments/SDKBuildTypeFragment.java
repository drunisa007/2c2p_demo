package com.ccpp.pgw.android.demo.merchant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.adapters.SDKBuildTypeViewAdapter;
import com.ccpp.pgw.android.demo.merchant.callback.RecyclerViewItemCallback;
import com.ccpp.pgw.android.demo.merchant.enums.SDKBuildType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class SDKBuildTypeFragment extends BaseFragment implements RecyclerViewItemCallback<SDKBuildType> {

    private Unbinder mUnBinder;

    @BindView(R.id.rv_list) RecyclerView mSDKBuildTypeRecyclerView;

    private ArrayList<SDKBuildType> mSDKBuildTypeList = new ArrayList<>();
    private SDKBuildTypeViewAdapter mSDKBuildTypeViewAdapter;

    public SDKBuildTypeFragment() { }

    public static SDKBuildTypeFragment newInstance() {

        SDKBuildTypeFragment fragment = new SDKBuildTypeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sdk_build_type, null);

        mUnBinder = ButterKnife.bind(this , view);

        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateToolBarTitle(getString(R.string.app_name));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mUnBinder.unbind();
    }

    private void init() {

        initLayout();
        initData();
    }

    private void initLayout() {

        mSDKBuildTypeViewAdapter = new SDKBuildTypeViewAdapter(getActivity(), mSDKBuildTypeList, this);

        mSDKBuildTypeRecyclerView.setHasFixedSize(true);
        mSDKBuildTypeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSDKBuildTypeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSDKBuildTypeRecyclerView.setAdapter(mSDKBuildTypeViewAdapter);
    }

    private void initData() {

        mSDKBuildTypeList.clear();
        mSDKBuildTypeList.add(SDKBuildType.CORE_SDK);
//        mSDKBuildTypeList.add(SDKBuildType.CORE_UI_SDK); //For future use

        mSDKBuildTypeViewAdapter.notifyDataSetChanged();
    }

    /**
     * from RecyclerViewItemCallback
     */
    @Override
    public void onClick(SDKBuildType sdkBuildType) {

        replaceFragment(FeatureCategoryFragment.newInstance(sdkBuildType));
    }
}
