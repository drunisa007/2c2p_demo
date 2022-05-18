package com.ccpp.pgw.android.demo.merchant.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by DavidBilly PK on 27/9/18.
 */
public class PaymentTokenRequest {

    /**
     * Reference : https://developer.2c2p.com/docs/mobile-v4-api-parameters#section--payment-token-request-parameters-
     *
     * Version : 10.01
     * {
     *     "version": "10.01",
     *     "merchantID": "",
     *     "invoiceNo": "",
     *     "desc": "",
     *     "amount": "",
     *     "currencyCode": "",
     *     "paymentChannel": "",
     *     "userDefined1": "",
     *     "userDefined2": "",
     *     "userDefined3": "",
     *     "userDefined4": "",
     *     "userDefined5": "",
     *     "interestType": "",
     *     "productCode": "",
     *     "recurring": "",
     *     "invoicePrefix": "",
     *     "recurringAmount": "",
     *     "allowAccumulate": "",
     *     "maxAccumulateAmt": "",
     *     "recurringInterval": "",
     *     "recurringCount": "",
     *     "chargeNextDate": "",
     *     "promotion": "",
     *     "request3DS": "",
     *     "tokenizeOnly": "",
     *     "statementDescriptor": "",
     *     "nonceStr": "",
     *     "signature": ""
     * }
     */

    @Accessors(prefix = "m") @Getter @Setter @SerializedName("version") private String mVersion;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("merchantID") private String mMerchantID;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("invoiceNo") private String mInvoiceNo;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("desc") private String mDesc;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("amount") private String mAmount;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("currencyCode") private String mCurrencyCode;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("paymentChannel") private String mPaymentChannel;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("userDefined1") private String mUserDefined1;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("userDefined2") private String mUserDefined2;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("userDefined3") private String mUserDefined3;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("userDefined4") private String mUserDefined4;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("userDefined5") private String mUserDefined5;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("interestType") private String mInterestType;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("productCode") private String mProductCode;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("recurring") private String mRecurring;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("invoicePrefix") private String mInvoicePrefix;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("recurringAmount") private String mRecurringAmount;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("allowAccumulate") private String mAllowAccumulate;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("maxAccumulateAmt") private String mMaxAccumulateAmt;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("recurringInterval") private String mRecurringInterval;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("recurringCount") private String mRecurringCount;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("chargeNextDate") private String mChargeNextDate;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("promotion") private String mPromotion;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("request3DS") private String mRequest3DS;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("tokenizeOnly") private String mTokenizeOnly;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("statementDescriptor") private String mStatementDescriptor;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("nonceStr") private String mNonceStr;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("signature") private String mSignature;
}
