package com.ccpp.pgw.android.demo.merchant.app;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
final public class Constants {

    //MERCHANT SERVER
    public static final String HTTP_MERCHANT_SERVER_PAYMENT_TOKEN_URL = "/paymentToken";
    public static final String HTTP_MERCHANT_SERVER_PAYMENT_INQUIRY_URL = "/paymentInquiry";

    //ERROR
    public static final String INVALID_PAYMENT_TOKEN_OBJ = "Invalid payment token object";
    public static final String INVALID_PAYMENT_INQUIRY_OBJ = "Invalid payment inquiry object";
    public static final String INVALID_GSON_OBJ = "Invalid Gson object";
    public static final String INVALID_FRAGMENT_OBJ = "Invalid fragment object";

    //JSON KEY NAME
    public final static String JSON_NAME_TRANSACTION_DATE_TIME = "transactionDateTime";
}
