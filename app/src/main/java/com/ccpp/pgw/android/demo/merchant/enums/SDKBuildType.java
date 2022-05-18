package com.ccpp.pgw.android.demo.merchant.enums;

/**
 * Created by DavidBilly PK on 27/9/18.
 */
public enum SDKBuildType {

    CORE_SDK("PGW Core SDK (Non-UI)"), CORE_UI_SDK("PGW Core UI SDK (UI)");

    private String sdkBuildType;

    SDKBuildType(String sdkBuildType) {
        this.sdkBuildType = sdkBuildType;
    }

    public String getName() {
        return sdkBuildType;
    }
}
