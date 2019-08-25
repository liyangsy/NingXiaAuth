package com.auth.provider;

import org.json.JSONException;
import org.json.JSONObject;

public interface NodeBase {

    void updateNodeInfo(JSONObject obj) throws JSONException;
}
