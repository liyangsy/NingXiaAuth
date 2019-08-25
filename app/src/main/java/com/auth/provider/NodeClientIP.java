package com.auth.provider;

import org.json.JSONObject;

public class NodeClientIP implements NodeBase {
    public static String CLIENT_IP = "client_ip";
    public String client_ip = null;

    @Override
    public void updateNodeInfo(JSONObject obj) {

    }

    public void updateNodeInfo(String obj){

    }
}
