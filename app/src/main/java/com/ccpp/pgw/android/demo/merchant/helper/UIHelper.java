package com.ccpp.pgw.android.demo.merchant.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class UIHelper {

    public static ProgressDialog getProgressDialog(Context context) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }

    public static void showProgressDialog(final Fragment fragment, final ProgressDialog progressDialog) {

        if(fragment != null && fragment.getActivity() != null) {

            fragment.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if(fragment.isAdded() && progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                }
            });
        }
    }

    public static void dismissProgressDialog(final Fragment fragment, final ProgressDialog progressDialog) {

        if(fragment != null && fragment.getActivity() != null) {

            fragment.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if(fragment.isAdded() && progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    public static void setMessage(final Fragment fragment, final ProgressDialog progressDialog, final String message) {

        if(fragment != null && fragment.getActivity() != null) {

            fragment.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if(fragment.isAdded() && progressDialog != null) {

                        progressDialog.setMessage(message);
                    }
                }
            });
        }
    }
}
