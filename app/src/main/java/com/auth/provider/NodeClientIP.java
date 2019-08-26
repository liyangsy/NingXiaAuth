package com.auth.provider;

import android.util.Log;

import org.json.JSONObject;

public class NodeClientIP extends NodeBase {
    private final static String TAG = "NodeClientIP";
    public static String CLIENT_IP = "client_ip";
    public String client_ip = null;

    NodeClientIP() {
        super(TAG);
    }

    @Override
    public void updateNodeInfo(JSONObject obj) {

    }

    public void updateNodeInfo(String obj){
        Log.d(TAG, "update client ip Node");
        client_ip = obj;

        addToMap();
    }

    @Override
    void addToMap() {
        mMap.put(CLIENT_IP, client_ip);
        super.addToMap();
    }
}
