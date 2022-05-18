package com.ccpp.pgw.android.demo.merchant.helper;

import android.util.Log;

import com.ccpp.pgw.android.demo.merchant.utils.Base64;
import com.ccpp.pgw.sdk.android.model.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

/**
 * Created by DavidBilly PK on 27/9/18.
 */
public class StringHelper {

    private final static String TAG = StringHelper.class.getName();

    public byte[] base64Decode(final String input) {
        return Base64.getDecoder().decode(input);
    }

    public String base64Encode(final byte[] input) {
        return Base64.getEncoder().encodeToString(input).replace("\n", "");
    }

    public String extractPaymentToken(String response) {

        String decodedResponse = "";

        try {
            decodedResponse = new String(android.util.Base64.decode(response, android.util.Base64.DEFAULT));
        } catch (Exception e) {
            Log.e(TAG, "Payment Token Base64 decode error!");
        }

        String paymentToken = "";

        try {
            JSONObject jsonObject = new JSONObject(decodedResponse);
            paymentToken = jsonObject.optString(Constants.JSON_NAME_PAYMENT_TOKEN);
        } catch (Exception e) {
            Log.e(TAG, "Payment Token JSON error!");
        }

        if(paymentToken.isEmpty()) {
            Log.d(TAG, "Payment Token not found! JSON: " + decodedResponse);
        }

        return paymentToken;
    }

    public static String toJson(Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

       return gson.toJson(object);
    }

    public static void longLog(String veryLongString) {
        int MAX_LOG_LENGTH = 4000;

        // Split by line, then ensure each line can fit into Log's maximum
        // length.
        for (int i = 0, length = veryLongString.length(); i < length; i++) {
            int newline = veryLongString.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                Log.d(TAG, veryLongString.substring(i, end));
                i = end;
            } while (i < newline);
        }
    }
}
