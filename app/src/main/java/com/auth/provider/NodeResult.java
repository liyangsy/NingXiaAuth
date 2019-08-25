package com.auth.provider;

import org.json.JSONException;
import org.json.JSONObject;

public class NodeResult implements NodeBase{
    public static String STATE = "state";
    public static String SUB_STATE = "sub_state";
    public static String REASON = "reason";

    public String state = null;
    public String sub_state = null;
    public String reason = null;

    @Override
    public void updateNodeInfo(JSONObject obj) throws JSONException {
        reason = obj.getString(NodeResult.REASON);
        state = obj.getString(NodeResult.STATE);
        sub_state = obj.getString(NodeResult.SUB_STATE);
    }
}
