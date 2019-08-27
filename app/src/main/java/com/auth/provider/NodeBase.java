package com.auth.provider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NodeBase {
    public String TAG = null;
    Map<String, String> mMap = new HashMap<String, String>();

    NodeBase(String tag){
        TAG = tag;
    }
    void updateNodeInfo(JSONObject obj) throws JSONException{

    }

    void addToMap(){}

    void dump(StringBuffer sb){
        sb.append(TAG).append("\r\n");
        Iterator it = mMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            sb.append(key).append("=").append(val).append("\r\n");
        }
    }
    public String getItem(String key){
        return null;
    }
}
