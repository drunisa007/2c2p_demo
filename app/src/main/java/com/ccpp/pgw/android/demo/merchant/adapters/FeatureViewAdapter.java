package com.ccpp.pgw.android.demo.merchant.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.callback.RecyclerViewItemCallback;
import com.ccpp.pgw.android.demo.merchant.models.Feature;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class FeatureViewAdapter extends RecyclerView.Adapter<FeatureViewAdapter.FeatureViewHolder> {

    private ArrayList<Feature> mList;
    private Context mContext;
    private RecyclerViewItemCallback<Feature> mRecyclerViewItemCallback;

    public FeatureViewAdapter(Context context, ArrayList<Feature> list, RecyclerViewItemCallback<Feature> callback) {
        this.mContext = context;
        this.mList = list;
        this.mRecyclerViewItemCallback = callback;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public FeatureViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_feature_details, viewGroup, false);
        FeatureViewHolder viewHolder = new FeatureViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FeatureViewHolder viewHolder, final int position) {

        final int itemPosition = viewHolder.getAdapterPosition();

        Glide.with(mContext).load(mList.get(itemPosition).getIcon()).into(viewHolder.icon);

        viewHolder.name.setText(mList.get(itemPosition).getName());
        viewHolder.description.setText(mList.get(itemPosition).getDescription());

        viewHolder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewItemCallback.onClick(mList.get(itemPosition));
            }
        });
    }
 
    public class FeatureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_feature_icon)
        ImageView icon;
        @BindView(R.id.tv_feature_name)
        TextView name;
        @BindView(R.id.tv_feature_description)
        TextView description;
        @BindView(R.id.btn_feature_payment)
        Button pay;

        FeatureViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}