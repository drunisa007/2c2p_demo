package com.ccpp.pgw.android.demo.merchant.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.android.demo.merchant.callback.HTTPResponseCallback;
import com.ccpp.pgw.android.demo.merchant.helper.MerchantServerSimulator;
import com.ccpp.pgw.android.demo.merchant.helper.UIHelper;
import com.ccpp.pgw.android.demo.merchant.models.PaymentInquiryRequest;
import com.ccpp.pgw.sdk.android.callback.TransactionResultCallback;
import com.ccpp.pgw.sdk.android.core.authenticate.PGWJavaScriptInterface;
import com.ccpp.pgw.sdk.android.core.authenticate.PGWWebViewClient;
import com.ccpp.pgw.sdk.android.enums.APIResponseCode;
import com.ccpp.pgw.sdk.android.model.api.response.TransactionResultResponse;

public class WebViewFragment extends BaseFragment {

    /**
     * Reference : https://developer.2c2p.com/docs/mobile-v4-3ds-or-non-3ds#step-5
     */
    private final String TAG = WebViewFragment.class.getName();
    private static final String ARG_REDIRECT_URL = "ARG_REDIRECT_URL";

    private String mRedirectUrl;

    private MerchantServerSimulator mMerchantServerSimulator;

    ProgressDialog mProgressDialog;

    public WebViewFragment() { }

    public static WebViewFragment newInstance(String redirectUrl) {

        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REDIRECT_URL, redirectUrl);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMerchantServerSimulator = new MerchantServerSimulator();
        mProgressDialog = UIHelper.getProgressDialog(getActivity());

        if (getArguments() != null) {
            mRedirectUrl = getArguments().getString(ARG_REDIRECT_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Step 5: Authentication handling for 3DS payment.
        WebView webview = new WebView(getActivity());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new PGWWebViewClient());
        webview.addJavascriptInterface(new PGWJavaScriptInterface(mTransactionResultCallback), PGWJavaScriptInterface.JAVASCRIPT_TRANSACTION_RESULT_KEY);
        webview.loadUrl(mRedirectUrl);

        return webview;
    }

    private TransactionResultCallback mTransactionResultCallback = new TransactionResultCallback() {

        @Override
        public void onResponse(TransactionResultResponse response) {

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

            //Get error response and display error
            Log.e(TAG, error.getMessage());
        }
    };

    /**
     * Build for payment inquiry request
     *
     * @param transactionID
     * @return
     */
    private PaymentInquiryRequest buildPaymentInquiry(String transactionID) {

        //Construct payment inquiry request
        PaymentInquiryRequest request = new PaymentInquiryRequest();
        request.setTransactionID(transactionID);

        return request;
    }

    private void inquiry(String transactionID) {

        mProgressDialog.setMessage(getString(R.string.common_message_payment_result_message));
        UIHelper.showProgressDialog(WebViewFragment.this, mProgressDialog);

        //Step 6: Get payment result.
        mMerchantServerSimulator.inquiryPaymentResult(buildPaymentInquiry(transactionID), new HTTPResponseCallback() {

            @Override
            public void onResponse(String response) {

                UIHelper.dismissProgressDialog(WebViewFragment.this, mProgressDialog);

                replaceFragment(TransactionResultFragment.newInstance(response));
            }

            @Override
            public void onFailure(String response) {

                UIHelper.dismissProgressDialog(WebViewFragment.this, mProgressDialog);

                Log.e(TAG, response);
            }
        });
    }
}
