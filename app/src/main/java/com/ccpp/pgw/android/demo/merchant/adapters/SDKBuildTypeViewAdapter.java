package com.ccpp.pgw.android.demo.merchant.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.callback.RecyclerViewItemCallback;
import com.ccpp.pgw.android.demo.merchant.enums.SDKBuildType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class SDKBuildTypeViewAdapter extends RecyclerView.Adapter<SDKBuildTypeViewAdapter.ViewHolder> {

    private ArrayList<SDKBuildType> mList;
    private Context mContext;
    private RecyclerViewItemCallback<SDKBuildType> mRecyclerViewItemCallback;

    public SDKBuildTypeViewAdapter(Context context, ArrayList<SDKBuildType> list, RecyclerViewItemCallback<SDKBuildType> callback) {
        this.mContext = context;
        this.mList = list;
        this.mRecyclerViewItemCallback = callback;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sdk_build_type_details, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int itemPosition = viewHolder.getAdapterPosition();

        viewHolder.name.setText(mList.get(itemPosition).getName());
        viewHolder.itemHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewItemCallback.onClick(mList.get(itemPosition));
            }
        });
    }
 
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView icon;
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_description)
        TextView description;
        @BindView(R.id.rl_item_holder)
        RelativeLayout itemHolder;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}