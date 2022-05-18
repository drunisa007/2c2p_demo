package com.ccpp.pgw.android.demo.merchant.payment.core;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.callback.HTTPResponseCallback;
import com.ccpp.pgw.android.demo.merchant.helper.UIHelper;
import com.ccpp.pgw.android.demo.merchant.models.PaymentTokenRequest;
import com.ccpp.pgw.sdk.android.callback.PaymentOptionCallback;
import com.ccpp.pgw.sdk.android.core.PGWSDK;
import com.ccpp.pgw.sdk.android.enums.APIResponseCode;
import com.ccpp.pgw.sdk.android.model.api.request.PaymentOptionRequest;
import com.ccpp.pgw.sdk.android.model.api.response.PaymentOptionResponse;
import com.ccpp.pgw.sdk.android.model.option.Channel;

import java.util.ArrayList;

/**
 * Created by DavidBilly PK on 28/9/18.
 */
public class RetrievePaymentOptions extends BasePayment {

    private final String TAG = RetrievePaymentOptions.class.getName();

    /**
     * Reference : https://developer.2c2p.com/docs/mobile-v4-payment-options-api
     *             https://developer.2c2p.com/docs/mobile-v4-api-parameters#section--paymentoptionrequest-class-parameters-
     */
    public RetrievePaymentOptions(Fragment fragment) {
        super(fragment);
    }

    @Override
    PaymentTokenRequest buildPaymentToken() {

        //Construct payment token request
        PaymentTokenRequest request = new PaymentTokenRequest();
        request.setPaymentChannel("ALL");

        return request;
    }

    @Override
    public void execute() {

        UIHelper.setMessage(mFragment, mProgressDialog, mFragment.getString(R.string.common_message_payment_token_message));
        UIHelper.showProgressDialog(mFragment, mProgressDialog);

        //Step 1 : Get payment token
        mMerchantServerSimulator.getPaymentToken(buildPaymentToken(), new HTTPResponseCallback() {

            @Override
            public void onResponse(String response) {

                UIHelper.dismissProgressDialog(mFragment, mProgressDialog);

                //Submit payment
                submit(mStringHelper.extractPaymentToken(response));
            }

            @Override
            public void onFailure(String response) {

                UIHelper.dismissProgressDialog(mFragment, mProgressDialog);

                Log.e(TAG, response);
            }
        });
    }

    @Override
    void submit(String paymentToken) {

        UIHelper.setMessage(mFragment, mProgressDialog, mFragment.getString(R.string.common_message_payment_processing_message));
        UIHelper.showProgressDialog(mFragment, mProgressDialog);

        //Step 2: Construct payment option request.
        PaymentOptionRequest paymentOptionRequest = new PaymentOptionRequest();
        paymentOptionRequest.setPaymentToken(paymentToken);

        //Step 3: Retrieve payment options.
        PGWSDK.getInstance().paymentOption(paymentOptionRequest, new PaymentOptionCallback() {

            @Override
            public void onResponse(PaymentOptionResponse response) {

                UIHelper.dismissProgressDialog(mFragment, mProgressDialog);

                if(response.getResponseCode().equals(APIResponseCode.API_SUCCESS)) {

                    ArrayList<Channel> channels = response.getChannels();

                    for(Channel channel : channels) {
                        String name = channel.getName();
                        String iconUrl = channel.getIconUrl();
                        String paymentChannel = channel.getPaymentChannel();

                        Log.d(TAG, "Enabled Payment channel : " + name);
                    }
                } else {

                    //Get error response and display error
                    Log.e(TAG, response.getResponseCode() + " :: " +response.getResponseDescription());
                }
            }

            @Override
            public void onFailure(Throwable error) {

                UIHelper.dismissProgressDialog(mFragment, mProgressDialog);

                //Get error response and display error
                Log.e(TAG, error.getMessage());
            }
        });
    }

    @Override
    void inquiry(String transactionID) {
        //no required for this API
    }
}
