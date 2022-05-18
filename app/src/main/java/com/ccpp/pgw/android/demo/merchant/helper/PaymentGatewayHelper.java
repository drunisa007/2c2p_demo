package com.ccpp.pgw.android.demo.merchant.helper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by DavidBilly PK on 27/9/18.
 */
public class PaymentGatewayHelper {

    private final String CHARSET = "UTF-8";

    //For HASHING
    private final String ALGORITHM_SHA = "HmacSHA256";

    public PaymentGatewayHelper() { }

    public String generateSignature(String secretKey, String jsonString) {

        ArrayList<String> stringArrayList = jsonToArray(jsonString);
        ArrayList<String> sortedStringArrayList = sortArray(stringArrayList);
        String arrayString = arrayToString(sortedStringArrayList);

        return hashSignature(secretKey, arrayString);
    }

    private ArrayList<String> jsonToArray(String jsonString) {

        ArrayList<String> list = new ArrayList<>();

        try {
            list.clear();

            JSONObject object = new JSONObject(jsonString);
            Iterator<String> keys = object.keys();

            while (keys.hasNext()) {
                list.add(object.getString(keys.next()));
            }
        } catch (Exception e) { }

        return list;
    }

    private ArrayList<String> sortArray(ArrayList<String> list) {

        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);

        return list;
    }

    private String arrayToString(ArrayList<String> list) {

        String arrayString = "";

        for(String value : list) {
           arrayString = arrayString + value;
        }

        return arrayString;
    }

    private String hashSignature(String secretKey, String arrayString) {

        String hashedSignature = "";

        try {

            Mac sha256HMAC = Mac.getInstance(ALGORITHM_SHA);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(CHARSET), ALGORITHM_SHA);
            sha256HMAC.init(secretKeySpec);
            hashedSignature = bytesToHex(sha256HMAC.doFinal(arrayString.getBytes(CHARSET))).toUpperCase();
        } catch (Exception e) { e.printStackTrace(); }

        return hashedSignature;
    }

    private String bytesToHex(byte[] bytes) {

        final char[] hexArray = "0123456789abcdef".toCharArray();

        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }
}
