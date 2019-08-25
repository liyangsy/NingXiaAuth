package com.auth.provider;

import com.squareup.okhttp.Response;

public interface AuthInfo {
    String HTTP = "http://";
    String DEVICE_AUTH_ADDR = "/cms4.38/nn_cms_view/vds/n215_a.php?";
    String TOKEN_AUTH_ADDR = "/cms4.39/nn_cms_view/vds/n215_a.php?";
    String SUFFIX = "nns_output_type=json";

    String SUB_NODE_RESULT = "result";
    String SUB_NODE_AUTH = "auth";
    String SUB_NODE_USER = "user";
    String SUB_NODE_DEVICE = "device";
    String SUB_NODE_CLIENT_IP = "client_ip";
    String SUB_NODE_BOSS_USER = "boss_user";

    boolean updateAuthInfo();
    boolean updateAuthInfo(Response response);
    String formateUrl();
}
