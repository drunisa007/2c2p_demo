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
import com.ccpp.pgw.android.demo.merchant.adapters.FeatureViewAdapter;
import com.ccpp.pgw.android.demo.merchant.callback.RecyclerViewItemCallback;
import com.ccpp.pgw.android.demo.merchant.enums.SDKBuildType;
import com.ccpp.pgw.android.demo.merchant.helper.FeatureCategoryHelper;
import com.ccpp.pgw.android.demo.merchant.models.Feature;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class FeatureCategoryFragment extends BaseFragment implements RecyclerViewItemCallback<Feature> {

    private final String TAG = FeatureCategoryFragment.class.getName();

    public static final String ARG_SDK_BUILD_TYPE = "ARG_SDK_BUILD_TYPE";

    private Unbinder mUnBinder;

    @BindView(R.id.rv_list) RecyclerView mFeatureRecyclerView;

    private ArrayList<Feature> mFeatureList = new ArrayList<>();
    private FeatureViewAdapter mFeatureViewAdapter;

    private SDKBuildType mSDKBuildType;

    private FeatureCategoryHelper mFeatureCategoryHelper;

    public FeatureCategoryFragment() { }

    public static FeatureCategoryFragment newInstance(SDKBuildType sdkBuildType) {

        FeatureCategoryFragment fragment = new FeatureCategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SDK_BUILD_TYPE, sdkBuildType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mSDKBuildType = (SDKBuildType) getArguments().getSerializable(ARG_SDK_BUILD_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_feature_category, null);

        mUnBinder = ButterKnife.bind(this , view);

        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateToolBarTitle(mSDKBuildType.getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mUnBinder.unbind();
    }

    private void init() {

        mFeatureCategoryHelper = new FeatureCategoryHelper();

        initLayout();
        initData();
    }

    private void initLayout() {

        mFeatureViewAdapter = new FeatureViewAdapter(getActivity(), mFeatureList, this);

        mFeatureRecyclerView.setHasFixedSize(true);
        mFeatureRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFeatureRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFeatureRecyclerView.setAdapter(mFeatureViewAdapter);
    }

    private void initData() {

        mFeatureList.clear();
        mFeatureList.addAll(mFeatureCategoryHelper.getList(mSDKBuildType));

        mFeatureViewAdapter.notifyDataSetChanged();
    }

    /**
     * from RecyclerViewItemCallback
     */
    @Override
    public void onClick(Feature feature) {

        mFeatureCategoryHelper.redirect(this, feature);
    }
}
