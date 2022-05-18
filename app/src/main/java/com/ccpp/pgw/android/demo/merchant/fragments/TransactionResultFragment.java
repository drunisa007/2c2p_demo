package com.ccpp.pgw.android.demo.merchant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ccpp.pgw.android.demo.merchant.R;
import com.ccpp.pgw.sdk.android.enums.APIResponseCode;
import com.ccpp.pgw.sdk.android.model.Constants;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DavidBilly PK on 24/4/18.
 */
public class TransactionResultFragment extends BaseFragment {

    private static final String TAG = TransactionResultFragment.class.getName();

    private static final String ARG_TRANSACTION_RESULT = "ARG_TRANSACTION_RESULT";

    private String mPaymentInquiryResponse;

    @BindView(R.id.tv_transaction_invoice_no) TextView mInvoiceNo;
    @BindView(R.id.tv_transaction_amount) TextView mAmount;
    @BindView(R.id.tv_transaction_datetime) TextView mDateTime;
    @BindView(R.id.tv_transaction_payment_type) TextView mPaymentType;
    @BindView(R.id.tv_transaction_masked_pan) TextView mMaskedPan;
    @BindView(R.id.tv_transaction_message) TextView mMessage;
    @BindView(R.id.tv_payment_inquiry_response) TextView mResponseMessageExpand;
    @BindView(R.id.transaction_result_message) TextView mTransactionResultMessage;
    @BindView(R.id.transaction_result_header) TextView mTransactionResultHeader;

    @BindView(R.id.ib_payment_inquiry_response_expand) ImageButton mResponseMessageExpandAction;

    private Unbinder mUnBinder;

    private boolean isExpanded = true;

    public TransactionResultFragment() { }

    public static TransactionResultFragment newInstance(String encodedResponse) {

        String decodedResponse = "{}";

        try {
            decodedResponse = new String(Base64.decode(encodedResponse, android.util.Base64.DEFAULT));
        } catch (Exception e) {
            Log.e(TAG, "Payment Inquiry Base64 decode error!");
        }

        TransactionResultFragment fragment = new TransactionResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRANSACTION_RESULT, decodedResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mPaymentInquiryResponse = getArguments().getString(ARG_TRANSACTION_RESULT, "{}");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateToolBarTitle(getString(R.string.transaction_result_title));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_transaction_result, null);

        mUnBinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    private void init() {
        initData();
    }

    private void initData() {

        try {
            JSONObject jsonObject = new JSONObject(mPaymentInquiryResponse);
            String responseCode = jsonObject.optString(Constants.JSON_NAME_RESP_CODE, "");

            /**
            * Reference : https://developer.2c2p.com/docs/mobile-v4-reference
            */
            boolean successPayment = responseCode.equals(APIResponseCode.API_SUCCESS) || responseCode.equals("4200"); //4200 Tokenization Successful.

            mTransactionResultHeader.setText(successPayment ? R.string.transaction_result_header_success : R.string.transaction_result_header_failed);
            mTransactionResultHeader.setTextColor(ContextCompat.getColor(getActivity(), successPayment ? R.color.transaction_success_color : R.color.transaction_failed_color));
            mTransactionResultMessage.setText(successPayment ? R.string.transaction_result_message_success : R.string.transaction_result_message_failed);
            mMessage.setTextColor(ContextCompat.getColor(getActivity(), successPayment ? R.color.transaction_success_color : R.color.transaction_failed_color));

            mInvoiceNo.setText(jsonObject.optString(Constants.JSON_NAME_INVOICE_NO, ""));
            mAmount.setText(jsonObject.optString(Constants.JSON_NAME_AMOUNT, ""));
            mDateTime.setText(jsonObject.optString(com.ccpp.pgw.android.demo.merchant.app.Constants.JSON_NAME_TRANSACTION_DATE_TIME, ""));
            mPaymentType.setText(jsonObject.optString(Constants.JSON_NAME_CHANNEL_CODE, ""));
            mMaskedPan.setText(jsonObject.optString(Constants.JSON_NAME_PAN, ""));
            mMessage.setText(jsonObject.optString(Constants.JSON_NAME_RESP_DESC, ""));

            String printJson;

            try {
                printJson = jsonObject.toString(4);
            } catch(Exception e) {
                Log.e(TAG, "Pretty JSON error!");

                printJson = mPaymentInquiryResponse;
            }

            mResponseMessageExpand.setText(printJson);
        } catch(Exception e) {
            Log.e(TAG, "Payment Inquiry JSON error!");
        }
    }

    @OnClick(R.id.btn_close)
    protected void onClickPaymentCompleted() {
        replaceFragment(SDKBuildTypeFragment.newInstance());
    }

    @OnClick(R.id.ib_payment_inquiry_response_expand)
    protected void onClickResponseExpand() {

        if(!isExpanded) {
            isExpanded = true;

            mResponseMessageExpand.setVisibility(View.VISIBLE);

            mResponseMessageExpandAction.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_response_close));
        } else {
            isExpanded = false;

            mResponseMessageExpand.setVisibility(View.GONE);

            mResponseMessageExpandAction.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_response_expand));
        }
    }
}
