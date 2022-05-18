package com.ccpp.pgw.android.demo.merchant.callback;

/**
 * Created by DavidBilly PK on 1/10/18.
 */
public interface HTTPResponseCallback {

    void onResponse(String response);

    void onFailure(String response);
}
