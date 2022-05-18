package com.ccpp.pgw.android.demo.merchant.payment.core;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.callback.HTTPResponseCallback;
import com.ccpp.pgw.android.demo.merchant.helper.UIHelper;
import com.ccpp.pgw.android.demo.merchant.models.PaymentTokenRequest;
import com.ccpp.pgw.sdk.android.callback.PaymentOptionDetailCallback;
import com.ccpp.pgw.sdk.android.core.PGWSDK;
import com.ccpp.pgw.sdk.android.enums.APIResponseCode;
import com.ccpp.pgw.sdk.android.enums.PaymentChannel;
import com.ccpp.pgw.sdk.android.model.api.request.PaymentOptionDetailRequest;
import com.ccpp.pgw.sdk.android.model.api.response.PaymentOptionDetailResponse;
import com.ccpp.pgw.sdk.android.model.option.CardType;
import com.ccpp.pgw.sdk.android.model.option.CreditCardOptionDetail;

/**
 * Created by DavidBilly PK on 28/9/18.
 */
public class RetrievePaymentOptionDetails extends BasePayment {

    private final String TAG = RetrievePaymentOptionDetails.class.getName();

    /**
     * Reference : https://developer.2c2p.com/docs/mobile-v4-payment-options-details-api
     *             https://developer.2c2p.com/docs/mobile-v4-api-parameters#section--paymentoptiondetailrequest-class-parameters-
     */
    public RetrievePaymentOptionDetails(Fragment fragment) {
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

        //Step 2: Construct payment option details request.
        PaymentOptionDetailRequest paymentOptionDetailRequest = new PaymentOptionDetailRequest();
        paymentOptionDetailRequest.setPaymentToken(paymentToken);
        paymentOptionDetailRequest.setPaymentChannel(PaymentChannel.CREDIT_CARD);

        //Step 3: Retrieve payment option details.
        PGWSDK.getInstance().paymentOptionDetail(paymentOptionDetailRequest, new PaymentOptionDetailCallback() {

            @Override
            public void onResponse(PaymentOptionDetailResponse response) {

                UIHelper.dismissProgressDialog(mFragment, mProgressDialog);

                if(response.getResponseCode().equals(APIResponseCode.API_SUCCESS)) {

                    CreditCardOptionDetail creditCardOptionDetail = response.getCreditCardOptionDetail();
                    validateCreditCard(creditCardOptionDetail);
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

    /**
     * Example for validate credit card input by using Retrieve payment option details API
     *
     * @param creditCardOptionDetail
     */
    private void validateCreditCard(CreditCardOptionDetail creditCardOptionDetail) {

        String userInputCardNumber = "4111111111111111";

        stopLoop:
        for(String bin : creditCardOptionDetail.getBins()) {

            //Do validate for supported card number
            if(userInputCardNumber.startsWith(bin)) {

                for(CardType cardType : creditCardOptionDetail.getCardTypes()) {

                    //Validate for card number length
                    if(cardType.getPrefixes().contains(bin)) {

                        if(userInputCardNumber.length() < cardType.getMinLength()
                                || userInputCardNumber.length() > cardType.getMaxLength()) {

                            //Invalid card number length
                            Log.d(TAG, "ValidateCreditCard : Invalid.");
                        } else {
                            //Valid credit card number
                            Log.d(TAG, "ValidateCreditCard : Valid.");
                        }

                        break stopLoop;
                    }
                }
            }
        }
    }
}
