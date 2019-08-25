package com.auth.provider;

import com.squareup.okhttp.Response;

public final class DeviceAuthRequestInfo implements AuthInfo {
    private final String TAG = "DeviceAuthInfo";

    private final String NNS_FUNC = "nns_func";
    private final String NNS_DEVICE_ID = "&nns_device_id=";
    private final String NNS_MAC_ID = "&nns_mac_id=";
    private final String NNS_VERSION = "&nns_version=";
    private final String NNS_OUTPUT_TYPE = "&nns_output_type=";
    //for request
    private String nns_func = null;
    private String nns_device_id = null;
    private String nns_mac_id = null;
    private String nns_version = null;
    private String nns_user_id = null;
    private String nns_user_password = null;
    private String nns_webtoken = null;
    private String nns_smart_card = null;
    private String nna_clt_data = null;
    private String nns_product_lists = null;
    private String nns_output_type = null;
    private String nns_net_id = null;
    private String nns_app_mode = null;
    private String nns_area_code = null;
    private String nns_expired_time = null;
    private SystemInfo mSystemInfo = null;
    public DeviceAuthRequestInfo(){
        nns_func = "scaaa_device_auth";
        mSystemInfo = SystemInfo.getInstance();
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
    public String formateUrl() {
        StringBuffer sb = new StringBuffer();
        sb.append(HTTP).append(SystemInfo.KEY_REMOTE_SERVER_HW)
                .append(DEVICE_AUTH_ADDR).append(NNS_FUNC)
                .append(nns_func).append(NNS_DEVICE_ID)
                .append(mSystemInfo.getSystemInfor(SystemInfo.KEY_DEVICE_ID,"1111111"))
                .append(NNS_MAC_ID).append(mSystemInfo.getSystemInfor(SystemInfo.KEY_MAC,"11:22:33:44:55:66"))
                .append(NNS_VERSION).append(mSystemInfo.getSystemInfor(SystemInfo.KEY_SOFTWARE_VERSION, "123456"))
                .append(SUFFIX);
        return sb.toString();
    }

    @Override
    public void dump(StringBuffer sb) {

    }
}
