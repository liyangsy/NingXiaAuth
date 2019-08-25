package com.auth.provider;

import org.json.JSONException;
import org.json.JSONObject;

public class NodeResult extends NodeBase {
    private static final String TAG = "NodeResult";
    public static String STATE = "state";
    public static String SUB_STATE = "sub_state";
    public static String REASON = "reason";

    public String state = null;
    public String sub_state = null;
    public String reason = null;

    NodeResult() {
        super(TAG);
    }

    @Override
    public void updateNodeInfo(JSONObject obj) throws JSONException {
        reason = obj.getString(NodeResult.REASON);
        state = obj.getString(NodeResult.STATE);
        sub_state = obj.getString(NodeResult.SUB_STATE);

        mMap.put(NodeResult.REASON, reason);
        mMap.put(NodeResult.SUB_STATE, sub_state);
        mMap.put(NodeResult.STATE, state);

        addToMap();
    }

    @Override
    void addToMap() {
        mMap.put(REASON, reason);
        mMap.put(STATE, state);
        mMap.put(SUB_STATE, sub_state);
        super.addToMap();
    }
}
