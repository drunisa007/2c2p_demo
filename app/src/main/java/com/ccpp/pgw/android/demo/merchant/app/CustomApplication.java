package com.ccpp.pgw.android.demo.merchant.app;

import android.app.Application;

import com.ccpp.pgw.sdk.android.core.PGWSDK;
import com.ccpp.pgw.sdk.android.enums.APIEnvironment;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class CustomApplication extends Application {

    /**
     * Reference : https://developer.2c2p.com/docs/mobile-v4-initial-sdk
     *             https://developer.2c2p.com/docs/mobile-v4-api-parameters#section--pgwsdk-builder-class-parameters-
     */
    @Override
    public void onCreate() {
        super.onCreate();

        initPaymentSDK();
    }

    /**
     * init 2C2P PGW SDK
     */
    private void initPaymentSDK() {

        //Change to your merchant info on MerchantConfig class
        PGWSDK.builder(this)
                .setMerchantID(MerchantConfig.MERCHANT_ID)
                .setAPIEnvironment(APIEnvironment.SANDBOX)
                .init();
    }
}
