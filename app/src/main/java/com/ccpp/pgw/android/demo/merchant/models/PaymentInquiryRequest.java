package com.ccpp.pgw.android.demo.merchant.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by DavidBilly PK on 27/9/18.
 */
public class PaymentInquiryRequest {

    /**
     * Reference : https://developer.2c2p.com/docs/mobile-v4-api-parameters#section--payment-inquiry-request-parameters-
     * Version : 1.1
     * {
     *    "version": "1.1",
     *    "merchantID": "",
     *    "transactionID": "",
     *    "invoiceNo": "",
     *    "signature": ""
     * }
     */

    @Accessors(prefix = "m") @Getter @Setter @SerializedName("version") private String mVersion;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("merchantID") private String mMerchantID;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("invoiceNo") private String mInvoiceNo;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("transactionID") private String mTransactionID;
    @Accessors(prefix = "m") @Getter @Setter @SerializedName("signature") private String mSignature;
}
