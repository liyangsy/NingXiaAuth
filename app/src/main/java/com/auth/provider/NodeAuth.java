package com.auth.provider;

import org.json.JSONException;
import org.json.JSONObject;

public class NodeAuth implements NodeBase{
    public static String WEB_TOKEN = "web_token";
    public static String EXPIRES_IN = "expires_in";
    public static String VALID_TIME = "valid_time";
    public static String REFRESH_TIME = "refresh_time";
    public static String TOKEN = "token";

    public String web_token = null;
    public String expires_in = null;
    public String valid_time = null;
    public String refresh_time = null;
    public String token = null;

    public String getWebToken(){
        return web_token;
    }

    @Override
    public void updateNodeInfo(JSONObject obj) throws JSONException {
        web_token = obj.getString(NodeAuth.WEB_TOKEN);
        expires_in = obj.getString(NodeAuth.EXPIRES_IN);
        valid_time = obj.getString(NodeAuth.VALID_TIME);
        refresh_time = obj.getString(NodeAuth.REFRESH_TIME);
        token = obj.getString(NodeAuth.TOKEN);
    }
}
