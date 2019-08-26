package com.auth.provider;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class HttpManager {
    private final String TAG = "HttpManager";

    private Callback calback = null;
    private static HttpManager mInstance = null;
    private OkHttpClient mOkHttpClient = null;

    private HttpManager(){
        mOkHttpClient = new OkHttpClient();
    }

    public static HttpManager getInstance(){
        if (mInstance == null){
            mInstance = new HttpManager();
        }
        return mInstance;
    }

    public boolean setCallBack(HttpCallBack cb){
        calback = cb;
        return true;
    }

    public void  postUrl(String url, Map<?, ?>map){
        Log.d(TAG, "postUrl: " + url);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (map != null){
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                builder.add(key, val);
            }
        }

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (calback != null)
                    calback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(calback != null){
                    calback.onResponse(response);
                }
            }
        });

    }

    public void get(String url){
//        String temp = "http://192.168.0.100/debug/app-debug.apk";
        Log.d(TAG, "get url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (calback != null)
                    calback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(calback != null){
                    calback.onResponse(response);
                }
            }
        });

    }

    public interface HttpCallBack extends Callback{
        @Override
        void onFailure(Request request, IOException e);

        @Override
        void onResponse(Response response) throws IOException;
    }
}
