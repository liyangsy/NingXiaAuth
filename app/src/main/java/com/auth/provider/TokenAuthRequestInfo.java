package com.auth.provider;

import com.squareup.okhttp.Response;

public class TokenAuthRequestInfo implements AuthInfo {
    private final String TAG = "TokenAuthRequestInfo";

    private final String NNS_FUNC = "nns_func=";
    private final String NNS_USER_ID = "&nns_user_id=";
    private final String NNS_WEBTOKEN = "&nns_webtoken=";
    private final String NNS_VERSION = "&nns_version=";
    private final String NNS_OUTPUT_TYPE = "&nns_output_type=";

    private String nns_func = null;
    private String nns_user_id = null;
    private String nns_device_id = null;
    private String nns_webtoken = null;
    private String nns_version = null;
    private String nns_output_type = null;
    private SystemInfo mSystemInfo = null;
    private DeviceAuthResponseInfor mDeviceAuthResponseInfo = null;

    public TokenAuthRequestInfo(){
        nns_func = "scaaa_auth_token";
        mSystemInfo = SystemInfo.getInstance();
        mDeviceAuthResponseInfo = DeviceAuthResponseInfor.getInstance();
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
    public boolean updateAuthInfo(String response) {
        return false;
    }

    @Override
    public String formateUrl() {
        StringBuffer sb = new StringBuffer();
        NodeAuth mAuth = mDeviceAuthResponseInfo.getDeviceAuthResponseInfo_Auth();
        NodeUser mUser = mDeviceAuthResponseInfo.getDeviceAuthResponseInfo_User();
        sb.append(HTTP).append(mSystemInfo.getSystemInfor(SystemInfo.KEY_REMOTE_SERVER_HW, "1111111"))
                .append(TOKEN_AUTH_ADDR).append(NNS_FUNC)
                .append(nns_func).append(NNS_USER_ID).append(mUser.getUserId())
                .append(NNS_WEBTOKEN).append(mAuth.getWebToken()).append(NNS_VERSION)
                .append(mSystemInfo.getSystemInfor(SystemInfo.KEY_SOFTWARE_VERSION, "123456"))
                .append(SUFFIX);
        return sb.toString();
    }

    @Override
    public void dump(StringBuffer sb) {

    }

    public String formateRefreshTokenUrl(){
            nns_func = "scaaa_refresh_token";
            StringBuffer sb = new StringBuffer();
            NodeAuth mAuth = mDeviceAuthResponseInfo.getDeviceAuthResponseInfo_Auth();
            sb.append(HTTP).append(mSystemInfo.getSystemInfor(SystemInfo.KEY_REMOTE_SERVER_HW, "1111111"))
                    .append(TOKEN_AUTH_ADDR).append(NNS_FUNC)
                    .append(nns_func).append(NNS_USER_ID).append(mSystemInfo.getSystemInfor(SystemInfo.KEY_USER_ID, "123"))
                    .append(NNS_WEBTOKEN).append(mAuth.getWebToken()).append(NNS_VERSION)
                    .append(mSystemInfo.getSystemInfor(SystemInfo.KEY_SOFTWARE_VERSION, "123456"))
                    .append(SUFFIX);
            return sb.toString();
        }
}
