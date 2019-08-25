package com.auth.provider;

import android.util.Log;

import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenAuthResponseInfo implements AuthInfo{
    private final String TAG = "TokenAuthResponseInfo";
    private NodeResult mNodeResult = null;
    private NodeAuth mNodeAuth = null;
    private NodeClientIP mNodeClientIP = null;

    private final String SUCCESS = "300000";

    private static TokenAuthResponseInfo mInstance = null;

    private TokenAuthResponseInfo(){
        mNodeResult = new NodeResult();
        mNodeAuth = new NodeAuth();
        mNodeClientIP = new NodeClientIP();
    }

    public static TokenAuthResponseInfo getInstance(){
        if(mInstance == null){
            mInstance = new TokenAuthResponseInfo();
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
                updateTokenAuthSuccess(obj);
            }else{
                updateTokenAuthFailed(obj);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Translate body to Json failed!");
            e.printStackTrace();
        }

        return false;
    }

    private void updateTokenAuthSuccess(JSONObject obj){
        try {
            JSONObject sub_result = obj.getJSONObject(SUB_NODE_RESULT);
            updateTokenAuthResult(sub_result);
            JSONObject sub_auth = obj.getJSONObject(SUB_NODE_AUTH);
            updateTokenAuthAuth(sub_auth);
            String sub_clientIP = obj.getString(SUB_NODE_CLIENT_IP);
            updateTokenAuthClientIP(sub_clientIP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateTokenAuthFailed(JSONObject obj){
        //do nothing now
    }

    private void updateTokenAuthResult(JSONObject obj){
        try {
/*            mNodeResult.state = obj.getString(NodeResult.STATE);
            mNodeResult.sub_state = obj.getString(NodeResult.SUB_STATE);
            mNodeResult.reason = obj.getString(NodeResult.REASON);*/
            mNodeResult.updateNodeInfo(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void updateTokenAuthAuth(JSONObject obj){
        try {
/*            mNodeAuth.web_token = obj.getString(NodeAuth.WEB_TOKEN);
            mNodeAuth.expires_in = obj.getString(NodeAuth.EXPIRES_IN);
            mNodeAuth.valid_time = obj.getString(NodeAuth.VALID_TIME);
            mNodeAuth.refresh_time = obj.getString(NodeAuth.REFRESH_TIME);*/
            mNodeAuth.updateNodeInfo(obj);
        } catch (JSONException e) {
            Log.e(TAG, "Update token auth auth info failed");
            e.printStackTrace();
        }
    }
    private void updateTokenAuthClientIP(String obj){
        mNodeClientIP.updateNodeInfo(obj);
    }
    @Override
    public String formateUrl() {
        return null;
    }

    @Override
    public void dump(StringBuffer sb) {

    }
}
