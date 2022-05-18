package com.ccpp.pgw.android.demo.merchant.payment.core;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.callback.HTTPResponseCallback;
import com.ccpp.pgw.android.demo.merchant.fragments.BaseFragment;
import com.ccpp.pgw.android.demo.merchant.fragments.TransactionResultFragment;
import com.ccpp.pgw.android.demo.merchant.helper.UIHelper;
import com.ccpp.pgw.android.demo.merchant.models.PaymentTokenRequest;
import com.ccpp.pgw.sdk.android.builder.CreditCardPaymentBuilder;
import com.ccpp.pgw.sdk.android.builder.TransactionRequestBuilder;
import com.ccpp.pgw.sdk.android.callback.TransactionResultCallback;
import com.ccpp.pgw.sdk.android.core.PGWSDK;
import com.ccpp.pgw.sdk.android.enums.APIResponseCode;
import com.ccpp.pgw.sdk.android.model.api.request.TransactionRequest;
import com.ccpp.pgw.sdk.android.model.api.response.TransactionResultResponse;
import com.ccpp.pgw.sdk.android.model.payment.CreditCardPayment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DavidBilly PK on 28/9/18.
 */
public class RecurringPaymentPlan extends BasePayment {

    private final String TAG = RecurringPaymentPlan.class.getName();

    /**
     * Reference : https://developer.2c2p.com/docs/mobile-v4-recurring-payment-plan
     *             https://developer.2c2p.com/docs/mobile-v4-api-parameters#credit-card-payment-builder
     */
    public RecurringPaymentPlan(Fragment fragment) {
        super(fragment);
    }

    @Override
    PaymentTokenRequest buildPaymentToken() {

        //Construct payment token request
        PaymentTokenRequest request = new PaymentTokenRequest();
        request.setPaymentChannel("CC");
        request.setRequest3DS("N");

        //Enable RPP payment option
        request.setRecurring("Y"); //Enable / Disable RPP option
        request.setInvoicePrefix("demo" + (System.currentTimeMillis() / 1000L)); //RPP transaction invoice prefix
        request.setRecurringAmount("000000000100"); //Recurring amount
        request.setAllowAccumulate("Y"); //Allow failed authorization to be accumulated
        request.setMaxAccumulateAmt("000000001000"); //Maximum threshold of total accumulated amount
        request.setRecurringInterval("5"); //Recurring interval by no of days
        request.setRecurringCount("3"); //Number of Recurring occurance
        request.setChargeNextDate(getChargeNextDate()); //The first day to start recurring charges. format DDMMYYYY

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

        //Step 2: Construct credit card request.
        CreditCardPayment creditCardPayment = new CreditCardPaymentBuilder("4111111111111111")
                .setExpiryMonth(12)
                .setExpiryYear(2019)
                .setSecurityCode("123")
                .build();

        //Step 3: Construct transaction request.
        TransactionRequest transactionRequest = new TransactionRequestBuilder(paymentToken)
                .withCreditCardPayment(creditCardPayment)
                .build();

        //Step 4: Execute payment request.
        PGWSDK.getInstance().proceedTransaction(transactionRequest, new TransactionResultCallback() {

            @Override
            public void onResponse(TransactionResultResponse response) {

                UIHelper.dismissProgressDialog(mFragment, mProgressDialog);

                if(response.getResponseCode().equals(APIResponseCode.TRANSACTION_COMPLETED)) {

                    //Inquiry payment result by using transaction id.
                    inquiry(response.getTransactionID());
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

        UIHelper.setMessage(mFragment, mProgressDialog, mFragment.getString(R.string.common_message_payment_result_message));
        UIHelper.showProgressDialog(mFragment, mProgressDialog);

        //Step 5: Get payment result.
        mMerchantServerSimulator.inquiryPaymentResult(buildPaymentInquiry(transactionID), new HTTPResponseCallback() {

            @Override
            public void onResponse(String response) {

                UIHelper.dismissProgressDialog(mFragment, mProgressDialog);

                ((BaseFragment) mFragment).replaceFragment(TransactionResultFragment.newInstance(response));
            }

            @Override
            public void onFailure(String response) {

                UIHelper.dismissProgressDialog(mFragment, mProgressDialog);

                Log.e(TAG, response);
            }
        });
    }

    private String getChargeNextDate() {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        Date nextDate = c.getTime();

        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
        return format.format(nextDate);
    }
}
