package com.auth.provider;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

public class AuthManager {
    private final String TAG = "AuthManager";
    private AuthInfo mAuthInfor = null;
    private Handler mDataAccessHandler = null;
    private HttpManager mHttpManager = null;

    public AuthManager(Context context, AuthInfo auth, Handler handler){
        mAuthInfor = auth;
        mDataAccessHandler = handler;
        mHttpManager = HttpManager.getInstance();
//        mHttpManager.setCallBack(new AuthCallBack());

    }

    public void startDeviceAuth(){
        mHttpManager.setCallBack(new DeviceAuthCallBack());
        mHttpManager.postUrl(new DeviceAuthRequestInfo().formateUrl(), null);
        mDataAccessHandler.sendEmptyMessage(DataAccessService.ACTION_DEVICE_AUTH_STARTED);
    }
    //TODO:这里面的参数是什么？
    public void startTokenAuth(){
        mHttpManager.setCallBack(new TokenAuthCallBack());
        mHttpManager.postUrl(new TokenAuthRequestInfo().formateUrl(), null);
        mDataAccessHandler.sendEmptyMessage(DataAccessService.ACTION_TOKEN_AUTH_STARTED);
    }

    public void startTokenFresh(){
        mHttpManager.setCallBack(new TokenRefreshCallBack());
        mHttpManager.postUrl(new TokenAuthRequestInfo().formateRefreshTokenUrl(), null);
        mDataAccessHandler.sendEmptyMessage(DataAccessService.ACTION_TOKEN_AUTH_STARTED);
    }

    private class DeviceAuthCallBack implements HttpManager.HttpCallBack{

        @Override
        public void onFailure(Request request, IOException e) {
            Log.e(TAG, "Device auth failed!");
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_DEVICE_AUTH_DONE;
            msg.arg1 = DataAccessService.RESULT_DEVICE_AUTH_FAILED;
            msg.obj = request.toString();
            mDataAccessHandler.sendMessage(msg);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            DeviceAuthResponseInfor instance = DeviceAuthResponseInfor.getInstance();
            instance.updateAuthInfo(response);
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_DEVICE_AUTH_DONE;
            msg.arg1 = DataAccessService.RESULT_DEVICE_AUTH_SUCCESS;
            msg.obj = response.body().toString();
            Log.d(TAG, "Device auth success: " + response.body().toString());
            mDataAccessHandler.sendMessage(msg);

        }
    }

    private class TokenAuthCallBack implements HttpManager.HttpCallBack{
        @Override
        public void onResponse(Response response) throws IOException {
            TokenAuthResponseInfo instance = TokenAuthResponseInfo.getInstance();
            instance.updateAuthInfo(response);
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_TOKEN_AUTH_DONE;
            msg.arg1 = DataAccessService.RESULT_TOKEN_AUTH_SUCCESS;
            msg.obj = response.body().toString();
            mDataAccessHandler.sendMessage(msg);
            Log.d(TAG,"Token auth success :" + response.body().toString());
        }

        @Override
        public void onFailure(Request request, IOException e) {
            Log.e(TAG, "Token auth failed url: " + request.url() + "body: " + request.body());
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_TOKEN_AUTH_DONE;
            msg.arg1 = DataAccessService.RESULT_TOKEN_AUTH_FAILED;
            msg.obj = request.toString();
            mDataAccessHandler.sendMessage(msg);
        }
    }

    private class TokenRefreshCallBack implements HttpManager.HttpCallBack{
        @Override
        public void onResponse(Response response) throws IOException {
            TokenAuthResponseInfo instance = TokenAuthResponseInfo.getInstance();
            instance.updateAuthInfo(response);
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_TOKEN_REFRESH_DONE;
            msg.arg1 = DataAccessService.RESULT_TOKEN_REFRESH_SUCCESS;
            msg.obj = response.toString();
            mDataAccessHandler.sendMessage(msg);
            Log.d(TAG, "Token refresh success: " + response.body().toString());
        }

        @Override
        public void onFailure(Request request, IOException e) {
            Message msg = Message.obtain();
            msg.what = DataAccessService.ACTION_TOKEN_REFRESH_DONE;
            msg.arg1 = DataAccessService.RESULT_TOKEN_REFRESH_FAILED;
            msg.obj = request.toString();
            mDataAccessHandler.sendMessage(msg);
            Log.d(TAG, "Token refresh failed url:"+ request.url() +"body: " + request.body().toString());
        }
    }
}
