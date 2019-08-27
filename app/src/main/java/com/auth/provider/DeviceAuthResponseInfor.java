package com.auth.provider;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
        return false;
    }

    @Override
    public  boolean updateAuthInfo(String response) {
        try {
//            Log.d(TAG,"liyang response: " + response.toString());
            Log.d(TAG, "liyang " + response);
//            String response = "{\"result\":{\"state\":300000,\"sub_state\":300003,\"reason\":\"\\u8bbe\\u5907\\u8ba4\\u8bc1\\u6210\\u529f \"},\"auth\":{\"web_token\":\"f3441a922714188811166e783840be0b\",\"expires_in\":602972,\"valid_time\":\"2019-09-02T16:00:15+0800\",\"refresh_time\":\"60480\"},\"user\":{\"id\":\"UT-test\",\"name\":\"\\u5929\\u9091\\u6d4b\\u8bd5\",\"rank\":\"1\",\"email\":\"\",\"telephone\":\"\",\"addr\":\"\",\"age\":\"\",\"sex\":\"\",\"occupation\":\"\",\"device_id\":\"000004640001197018015C4A1FFC7005\",\"headimgurl\":\"\",\"first_use_time\":\"\",\"attribution\":\"\",\"user_from\":\"1\",\"user_level\":\"0\",\"boss_user_group\":\"0\",\"user_growth_value\":\"\",\"boss_top_box_id\":\"\",\"user_is_category\":\"\",\"boss_area_code\":\"\",\"boss_area_bind\":\"NO\",\"area_code\":\"\",\"nns_last_login_version\":\"STB1.1.0\",\"nns_last_login_mac\":\"5C4A1FFC7005\"},\"device\":{\"id\":\"000004640001197018015C4A1FFC7005\",\"name\":\"000004640001197018015C4A1FFC7005\",\"first_use_time\":\"2019-08-26 16:30:43\",\"create_time\":\"2019-08-26 10:56:17\",\"is_add_device\":1},\"client_ip\":\"10.101.7.62\"}";
            if(TextUtils.isEmpty(response)){
                Log.d(TAG, "liyang is empty");
                return false;
            }
            JSONObject obj = new JSONObject(response);
            JSONObject sub_result = obj.getJSONObject(SUB_NODE_RESULT);
            String result = sub_result.getString("state");
            if(result.equals(SUCCESS)){
                updateAuthInfoSuccess(obj);
            }else {
                updateAuthInfoFailed(obj);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Translate json failed");
            e.printStackTrace();
            return false;
        } /*catch (IOException e){
            Log.e(TAG, "ioexception");
            return false;
        }*/

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

    public NodeUser getDeviceAuthResponseInfo_User(){
        return mUser;
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

    @Override
    public void dump(StringBuffer sb) {

    }
}
