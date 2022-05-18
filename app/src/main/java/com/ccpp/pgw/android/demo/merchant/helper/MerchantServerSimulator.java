package com.ccpp.pgw.android.demo.merchant.helper;

import android.util.Log;

import com.ccpp.pgw.android.demo.merchant.app.Constants;
import com.ccpp.pgw.android.demo.merchant.app.MerchantConfig;
import com.ccpp.pgw.android.demo.merchant.callback.HTTPResponseCallback;
import com.ccpp.pgw.android.demo.merchant.models.PaymentInquiryRequest;
import com.ccpp.pgw.android.demo.merchant.models.PaymentTokenRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

/**
 * Created by DavidBilly PK on 27/9/18.
 */
public class MerchantServerSimulator {

    private final String TAG = MerchantServerSimulator.class.getName();

    /**
     * IMPORTANT : MerchantServerSimulator just for simulator how merchant server work on demo app, and DO NOT call payment token API directly from your application.
     */
    private Gson mGson;

    private HTTPHelper mHTTPHelper;
    private PaymentGatewayHelper mPaymentGatewayHelper;
    private StringHelper mStringHelper;

    public MerchantServerSimulator() {

        mHTTPHelper = new HTTPHelper();
        mPaymentGatewayHelper = new PaymentGatewayHelper();
        mStringHelper = new StringHelper();

        mGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    }

    /**
     * @param paymentTokenRequest
     * @param httpResponseCallback
     *
     * Reference : https://developer.2c2p.com/docs/mobile-v4-how-to-integrate
     *             https://developer.2c2p.com/docs/mobile-v4-api-parameters#section--payment-token-request-parameters-
     */
    public void getPaymentToken(PaymentTokenRequest paymentTokenRequest, HTTPResponseCallback httpResponseCallback) {

        //Request information
        String apiVersion = MerchantConfig.PAYMENT_TOKEN_API_VERSION;
        String nonceStr = UUID.randomUUID().toString();

        //Merchant's account information
        String mid = MerchantConfig.MERCHANT_ID; //Get MerchantID when opening account with 2C2P
        String secretKey = MerchantConfig.MERCHANT_SECRET_KEY; //Get SecretKey from 2C2P PGW dashboard

        //Transaction information
        String desc = "2 days 1 night hotel room";
        String invoiceNo = String.valueOf(System.currentTimeMillis() / 1000L);
        String currencyCode = MerchantConfig.MERCHANT_CURRENCY_CODE;
        String amount = "000000001000";

        //Set common values for all payments
        if(paymentTokenRequest != null) {

            paymentTokenRequest.setVersion(apiVersion);
            paymentTokenRequest.setNonceStr(nonceStr);
            paymentTokenRequest.setMerchantID(mid);
            paymentTokenRequest.setDesc(desc);
            paymentTokenRequest.setInvoiceNo(invoiceNo);
            paymentTokenRequest.setCurrencyCode(currencyCode);
            paymentTokenRequest.setAmount(amount);
        } else {
            httpResponseCallback.onFailure(Constants.INVALID_PAYMENT_TOKEN_OBJ);
        }

        if(mGson != null) {

            //Generate signature
            String paymentTokenRequestJson = mGson.toJson(paymentTokenRequest);
            paymentTokenRequest.setSignature(mPaymentGatewayHelper.generateSignature(secretKey, paymentTokenRequestJson));
            String finalPaymentTokenRequestJson = mGson.toJson(paymentTokenRequest);

            Log.d(TAG, "getPaymentToken JSON: " + finalPaymentTokenRequestJson);

            String encodedPaymentTokenRequestJson = mStringHelper.base64Encode(finalPaymentTokenRequestJson.getBytes());
            mHTTPHelper.requestPaymentToken(encodedPaymentTokenRequestJson, httpResponseCallback);
        } else {
            httpResponseCallback.onFailure(Constants.INVALID_GSON_OBJ);
        }
    }

    /**
     * @param paymentInquiryRequest
     * @param httpResponseCallback
     *
     * Reference : https://developer.2c2p.com/docs/mobile-v4-payment-inquiry-api
     *             https://developer.2c2p.com/docs/mobile-v4-api-parameters#section--payment-inquiry-response-parameters-
     */
    public void inquiryPaymentResult(PaymentInquiryRequest paymentInquiryRequest, HTTPResponseCallback httpResponseCallback) {

        //Request information
        String apiVersion = MerchantConfig.API_VERSION;

        //Merchant's account information
        String mid = MerchantConfig.MERCHANT_ID; //Get MerchantID when opening account with 2C2P
        String secretKey = MerchantConfig.MERCHANT_SECRET_KEY; //Get SecretKey from 2C2P PGW dashboard

        //Set common values for all payments
        if(paymentInquiryRequest != null) {

            paymentInquiryRequest.setVersion(apiVersion);
            paymentInquiryRequest.setMerchantID(mid);
        } else {
            httpResponseCallback.onFailure(Constants.INVALID_PAYMENT_INQUIRY_OBJ);
        }

        if(mGson != null) {

            //Generate signature
            String paymentInquiryRequestJson = mGson.toJson(paymentInquiryRequest);
            paymentInquiryRequest.setSignature(mPaymentGatewayHelper.generateSignature(secretKey, paymentInquiryRequestJson));
            String finalPaymentInquiryRequestJson = mGson.toJson(paymentInquiryRequest);

            Log.d(TAG, "inquiryPaymentResult JSON: " + finalPaymentInquiryRequestJson);

            String encodedPaymentInquiryRequestJson = mStringHelper.base64Encode(finalPaymentInquiryRequestJson.getBytes());
            mHTTPHelper.requestPaymentInquiry(encodedPaymentInquiryRequestJson, httpResponseCallback);
        } else {
            httpResponseCallback.onFailure(Constants.INVALID_GSON_OBJ);
        }
    }
}
