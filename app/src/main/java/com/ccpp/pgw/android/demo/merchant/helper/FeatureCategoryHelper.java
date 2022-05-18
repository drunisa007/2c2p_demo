package com.ccpp.pgw.android.demo.merchant.helper;

import android.support.v4.app.Fragment;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.enums.SDKBuildType;
import com.ccpp.pgw.android.demo.merchant.models.Feature;
import com.ccpp.pgw.android.demo.merchant.payment.core.CreditCard3DS;
import com.ccpp.pgw.android.demo.merchant.payment.core.CreditCardTokenization;
import com.ccpp.pgw.android.demo.merchant.payment.core.CreditCardNon3DS;
import com.ccpp.pgw.android.demo.merchant.payment.core.CreditCardTokenizationWithoutAuthorization;
import com.ccpp.pgw.android.demo.merchant.payment.core.CreditCardWithToken;
import com.ccpp.pgw.android.demo.merchant.payment.core.InstallmentPaymentPlan;
import com.ccpp.pgw.android.demo.merchant.payment.core.RecurringPaymentPlan;
import com.ccpp.pgw.android.demo.merchant.payment.core.RetrievePaymentOptionDetails;
import com.ccpp.pgw.android.demo.merchant.payment.core.RetrievePaymentOptions;

import java.util.ArrayList;

/**
 * Created by DavidBilly PK on 27/9/18.
 */
public class FeatureCategoryHelper {

    public FeatureCategoryHelper() { }

    public ArrayList<Feature> getList(SDKBuildType sdkBuildType) {

        ArrayList<Feature> arrayList = new ArrayList<>();

        if(sdkBuildType == SDKBuildType.CORE_SDK) {

            arrayList.clear();
            arrayList.add(new Feature(1, R.mipmap.icon_payment_credit_card_compat, "Credit card payment (Non 3DS)", "Normal payment without 3DS"));
            arrayList.add(new Feature(2, R.mipmap.icon_payment_credit_card_compat, "Credit card payment (3DS)", "Normal payment with 3DS"));
            arrayList.add(new Feature(3, R.mipmap.icon_payment_credit_card_compat, "Card tokenization payment", "Normal payment with card tokenization"));
            arrayList.add(new Feature(4, R.mipmap.icon_payment_credit_card_compat, "Card tokenization without authorization", "Card tokenization without payment process"));
            arrayList.add(new Feature(5, R.mipmap.icon_payment_credit_card_compat, "Credit card payment with card token", "Normal payment with card token"));
            arrayList.add(new Feature(6, R.mipmap.icon_payment_credit_card_compat, "IPP (Installment payment plan)", "Installment payment"));
            arrayList.add(new Feature(7, R.mipmap.icon_payment_credit_card_compat, "RPP (Recurring payment plan)", "Recurring payment"));
            arrayList.add(new Feature(8, R.drawable.icon_payment_option, "Retrieve payment options", "For retrieve enabled payment channel list"));
            arrayList.add(new Feature(9, R.drawable.icon_payment_option, "Retrieve payment option details", "For retrieve payment details by payment channel"));
        } else {
            //Unknown sdk build type, so do nothing.
        }

        return arrayList;
    }

    public void redirect(Fragment fragment, Feature feature) {

        if(feature.getId() == 1) {

            CreditCardNon3DS creditCardNon3DS = new CreditCardNon3DS(fragment);
            creditCardNon3DS.execute();
        } else if(feature.getId() == 2) {

            CreditCard3DS creditCard3DS = new CreditCard3DS(fragment);
            creditCard3DS.execute();
        } else if(feature.getId() == 3) {

            CreditCardTokenization creditCardTokenization = new CreditCardTokenization(fragment);
            creditCardTokenization.execute();
        } else if(feature.getId() == 4) {

            CreditCardTokenizationWithoutAuthorization creditCardTokenizationWithoutAuthorization = new CreditCardTokenizationWithoutAuthorization(fragment);
            creditCardTokenizationWithoutAuthorization.execute();
        } else if(feature.getId() == 5) {

            CreditCardWithToken creditCardWithToken = new CreditCardWithToken(fragment);
            creditCardWithToken.execute();
        } else if(feature.getId() == 6) {

            InstallmentPaymentPlan installmentPaymentPlan = new InstallmentPaymentPlan(fragment);
            installmentPaymentPlan.execute();
        } else if(feature.getId() == 7) {

            RecurringPaymentPlan recurringPaymentPlan = new RecurringPaymentPlan(fragment);
            recurringPaymentPlan.execute();
        } else if(feature.getId() == 8) {

            RetrievePaymentOptions retrievePaymentOptions = new RetrievePaymentOptions(fragment);
            retrievePaymentOptions.execute();
        } else if(feature.getId() == 9) {

            RetrievePaymentOptionDetails retrievePaymentOptionDetails = new RetrievePaymentOptionDetails(fragment);
            retrievePaymentOptionDetails.execute();
        } else {

            CreditCardNon3DS creditCardNon3DS = new CreditCardNon3DS(fragment);
            creditCardNon3DS.execute();
        }
    }
}
