package com.auth.provider;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

public class AuthManager {
    private final String TAG = "AuthManager";

    private HttpManager mHttpManager = null;
    private DataAccessService mDataService = null;


    public AuthManager(DataAccessService service){

        mHttpManager = HttpManager.getInstance();
        mDataService = service;
    }


    public void startDeviceAuth(){
        Log.d(TAG, "start DeviceAuth:" + new DeviceAuthRequestInfo().formateUrl());
        mHttpManager.setCallBack(new DeviceAuthCallBack());
        mHttpManager.get(new DeviceAuthRequestInfo().formateUrl());
        Log.d(TAG, "send empty msg start");
        if (mDataService == null) Log.d(TAG, "handler is null");
        mDataService.sendEmptyMessage(DataAccessService.ACTION_DEVICE_AUTH_STARTED);
        Log.d(TAG, "send empty msg end");
    }
    //TODO:这里面的参数是什么？
    public void startTokenAuth(){
        Log.d(TAG, "start token auth: " + new TokenAuthRequestInfo().formateUrl());
        mHttpManager.setCallBack(new TokenAuthCallBack());
        mHttpManager.get(new TokenAuthRequestInfo().formateUrl());
        mDataService.sendEmptyMessage(DataAccessService.ACTION_TOKEN_AUTH_STARTED);
    }

    public void startTokenFresh(){
        Log.d(TAG, "start token refresh");
        mHttpManager.setCallBack(new TokenRefreshCallBack());
        mHttpManager.get(new TokenAuthRequestInfo().formateRefreshTokenUrl());
        mDataService.sendEmptyMessage(DataAccessService.ACTION_TOKEN_AUTH_STARTED);
    }

    private class DeviceAuthCallBack implements HttpManager.HttpCallBack{

        @Override
        public void onFailure(Request request, IOException e) {
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_DEVICE_AUTH_DONE;
            msg.arg1 = DataAccessService.RESULT_DEVICE_AUTH_FAILED;
            msg.obj = request.toString();
            Log.e(TAG, "Device auth failed url: " + request.url() + " error info:" + e.getMessage());
            mDataService.sendMessage(msg);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            String body = response.body().string();
            DeviceAuthResponseInfor instance = DeviceAuthResponseInfor.getInstance();
            instance.updateAuthInfo(body);
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_DEVICE_AUTH_DONE;
            msg.arg1 = DataAccessService.RESULT_DEVICE_AUTH_SUCCESS;
            msg.obj = body;
            Log.d(TAG, "Device auth success: " + body);
            mDataService.sendMessage(msg);

        }
    }

    private class TokenAuthCallBack implements HttpManager.HttpCallBack{
        @Override
        public void onResponse(Response response) throws IOException {
            String body = response.body().string();
            TokenAuthResponseInfo instance = TokenAuthResponseInfo.getInstance();
            instance.updateAuthInfo(body);
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_TOKEN_AUTH_DONE;
            msg.arg1 = DataAccessService.RESULT_TOKEN_AUTH_SUCCESS;
            msg.obj = response.body().toString();
            mDataService.sendMessage(msg);
            Log.d(TAG,"Token auth success :" + response.body().toString());
        }

        @Override
        public void onFailure(Request request, IOException e) {
            Log.e(TAG, "Token auth failed url: " + request.url() + "error info" + e.getMessage());
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_TOKEN_AUTH_DONE;
            msg.arg1 = DataAccessService.RESULT_TOKEN_AUTH_FAILED;
            msg.obj = request.toString();
            mDataService.sendMessage(msg);
        }
    }

    private class TokenRefreshCallBack implements HttpManager.HttpCallBack{
        @Override
        public void onResponse(Response response) throws IOException {
            String body = response.body().string();
            TokenAuthResponseInfo instance = TokenAuthResponseInfo.getInstance();
            instance.updateAuthInfo(body);
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_TOKEN_REFRESH_DONE;
            msg.arg1 = DataAccessService.RESULT_TOKEN_REFRESH_SUCCESS;
            msg.obj = response.toString();
            mDataService.sendMessage(msg);
            Log.d(TAG, "Token refresh success: " + response.body().toString());
        }

        @Override
        public void onFailure(Request request, IOException e) {
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_TOKEN_REFRESH_DONE;
            msg.arg1 = DataAccessService.RESULT_TOKEN_REFRESH_FAILED;
            msg.obj = request.toString();
            mDataService.sendMessage(msg);
            Log.d(TAG, "Token refresh failed url:"+ request.url() + " error info:" + e.getMessage());
        }
    }
}
