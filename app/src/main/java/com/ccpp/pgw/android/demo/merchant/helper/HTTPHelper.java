package com.ccpp.pgw.android.demo.merchant.helper;

import com.ccpp.pgw.android.demo.merchant.app.Constants;
import com.ccpp.pgw.android.demo.merchant.callback.HTTPResponseCallback;
import com.ccpp.pgw.sdk.android.core.PGWSDK;
import com.ccpp.pgw.sdk.android.enums.APIEnvironment;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class HTTPHelper {

    private final int HTTP_TIME_OUT = 60;
    private final String HTTP_HEADER_MEDIA_TYPE = "application/json";

    private OkHttpClient mClient;

    public HTTPHelper () {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor);

        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", HTTP_HEADER_MEDIA_TYPE)
                        .header("Accept", HTTP_HEADER_MEDIA_TYPE)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        mClient = builder.build();
    }

    private void post(String url, RequestBody body, Callback callback) {

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = mClient.newCall(request);
        call.enqueue(callback);
    }

    public void requestPaymentToken(String encodedJson, final HTTPResponseCallback callback) {

        APIEnvironment apiEnvironment = PGWSDK.getInstance().getAPIEnvironment();

        RequestBody requestBody = RequestBody.create(MediaType.parse(HTTP_HEADER_MEDIA_TYPE), encodedJson);

        post(apiEnvironment.getName() + Constants.HTTP_MERCHANT_SERVER_PAYMENT_TOKEN_URL, requestBody, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFailure("get payment token failed.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    callback.onResponse(response.body().string());
                } else {
                    callback.onFailure("get payment token failed." + response.toString());
                }
            }
        });
    }

    public void requestPaymentInquiry(String encodedJson, final HTTPResponseCallback callback) {

        APIEnvironment apiEnvironment = PGWSDK.getInstance().getAPIEnvironment();

        RequestBody requestBody = RequestBody.create(MediaType.parse(HTTP_HEADER_MEDIA_TYPE), encodedJson);

        post(apiEnvironment.getName() + Constants.HTTP_MERCHANT_SERVER_PAYMENT_INQUIRY_URL, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFailure("get payment inquiry failed.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body().string());
                } else {
                    callback.onFailure("get payment inquiry failed.");
                }
            }
        });
    }
}
