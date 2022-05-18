package com.ccpp.pgw.android.demo.merchant.payment.core;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.ccpp.pgw.android.demo.merchant.helper.MerchantServerSimulator;
import com.ccpp.pgw.android.demo.merchant.helper.StringHelper;
import com.ccpp.pgw.android.demo.merchant.helper.UIHelper;
import com.ccpp.pgw.android.demo.merchant.models.PaymentInquiryRequest;
import com.ccpp.pgw.android.demo.merchant.models.PaymentTokenRequest;

/**
 * Created by DavidBilly PK on 28/9/18.
 */
abstract class BasePayment {

    abstract PaymentTokenRequest buildPaymentToken();
    public abstract void execute();
    abstract void submit(String paymentToken);
    abstract void inquiry(String transactionID);

    MerchantServerSimulator mMerchantServerSimulator;
    StringHelper mStringHelper;
    Fragment mFragment;

    ProgressDialog mProgressDialog;

    BasePayment(Fragment fragment) {

        mFragment = fragment;
        mMerchantServerSimulator = new MerchantServerSimulator();
        mStringHelper = new StringHelper();

        mProgressDialog = UIHelper.getProgressDialog(mFragment.getActivity());
    }

    /**
     * Build for payment inquiry request
     *
     * @param transactionID
     * @return
     */
    PaymentInquiryRequest buildPaymentInquiry(String transactionID) {

        //Construct payment inquiry request
        PaymentInquiryRequest request = new PaymentInquiryRequest();
        request.setTransactionID(transactionID);

        return request;
    }
}
