package com.auth.provider;

import android.util.Log;

import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class DeviceAuthResponseInfor implements AuthInfo {

    private final String TAG = "DeviceAuthResponseInfor";

    private final String SUCCESS = "300000";

    private static DeviceAuthResponseInfor mInstance = null;
    private NodeResult mResult = null;
    private NodeAuth mAuth = null;
    private NodeUser mUser = null;
    private NodeDevice mDevice = null;
    private NodeBossUser mBossUser = null;
    private NodeClientIP mClientIP = null;

    private DeviceAuthResponseInfor(){
        mResult = new NodeResult();
        mAuth = new NodeAuth();
        mUser = new NodeUser();
        mDevice = new NodeDevice();
        mBossUser = new NodeBossUser();
        mClientIP = new NodeClientIP();

    }

    public static DeviceAuthResponseInfor getInstance(){
        if(mInstance == null){
            mInstance = new DeviceAuthResponseInfor();
        }
        return mInstance;
    }

    @Override
    public boolean updateAuthInfo() {
        return false;
    }

    @Override
    public boolean updateAuthInfo(Response response) {
        try {
            JSONObject obj = new JSONObject(response.body().toString());
            JSONObject sub_result = obj.getJSONObject(SUB_NODE_RESULT);
            String result = sub_result.getString("result");
            if(result.equals(SUCCESS)){
                updateAuthInfoSuccess(obj);
            }else {
                updateAuthInfoFailed(obj);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Translate json failed");
            e.printStackTrace();
            return false;
        }

        return false;
    }

    private boolean updateAuthInfoSuccess(JSONObject obj){
        try {
            JSONObject sub_result = obj.getJSONObject(SUB_NODE_RESULT);
            updateResultSuccess(sub_result);
            JSONObject sub_auth = obj.getJSONObject(SUB_NODE_AUTH);
            updateAuth(sub_auth);
            JSONObject sub_user = obj.getJSONObject(SUB_NODE_USER);
            updateUserSuccess(sub_user);
            JSONObject sub_device = obj.getJSONObject(SUB_NODE_DEVICE);
            updateDevice(sub_device);
            String sub_client_ip = obj.getString(SUB_NODE_CLIENT_IP);
            updateClientIP(sub_client_ip);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "update auth success info failed");
            return false;
        }
        return true;
    }

    private boolean updateAuthInfoFailed(JSONObject obj){
        try {
            JSONObject sub_result = obj.getJSONObject(SUB_NODE_RESULT);
            updateResultFailed(sub_result);
            JSONObject sub_user = obj.getJSONObject(SUB_NODE_USER);
            updateUserFailed(sub_user);
            JSONObject sub_boss_user = obj.getJSONObject(SUB_NODE_BOSS_USER);
            updateBossUser(sub_boss_user);
            String sub_client_ip = obj.getString(SUB_NODE_CLIENT_IP);
            updateClientIP(sub_client_ip);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "update auth failed info failed");
            return false;
        }
        return true;
    }

    private void updateResultSuccess(JSONObject obj) throws JSONException {
        mResult.updateNodeInfo(obj);
    }

    private void updateAuth(JSONObject obj) throws JSONException {
        mAuth.updateNodeInfo(obj);
    }

    private void updateUserSuccess(JSONObject obj) throws JSONException {
        mUser.updateNodeInfo(obj);
    }

    private void updateDevice(JSONObject obj) throws JSONException {
        mDevice.updateNodeInfo(obj);
    }

    private void updateResultFailed(JSONObject obj) throws JSONException {
        mResult.updateNodeInfo(obj);
    }

    private void updateUserFailed(JSONObject obj) throws JSONException {
        mUser.updateNodeInfo(obj);
    }

    public NodeAuth getDeviceAuthResponseInfo_Auth(){
        return mAuth;
    }

    private void updateBossUser(JSONObject obj) throws JSONException {
        mBossUser.updateNodeInfo(obj);
    }
    private  void updateClientIP(String ip){
        mClientIP.updateNodeInfo(ip);
    }
    @Override
    public String formateUrl() {
        return null;
    }
}
