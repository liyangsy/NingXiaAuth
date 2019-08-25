package com.auth.provider;

import org.json.JSONException;
import org.json.JSONObject;

public class NodeBossUser implements NodeBase{
    public static String ID = "id";

    public String id = null;

    @Override
    public void updateNodeInfo(JSONObject obj) throws JSONException {
        id = obj.getString(NodeBossUser.ID);
    }
}
