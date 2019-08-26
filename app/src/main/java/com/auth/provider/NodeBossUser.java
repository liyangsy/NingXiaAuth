package com.auth.provider;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class NodeBossUser extends NodeBase {
    private static final String TAG = "NodeBossUser";
    public static String ID = "id";

    public String id = null;

    NodeBossUser() {
        super(TAG);
    }

    @Override
    public void updateNodeInfo(JSONObject obj) throws JSONException {
        Log.d(TAG, "update BossUser node");
        id = obj.getString(NodeBossUser.ID);

        addToMap();
    }

    @Override
    void addToMap() {
        mMap.put(ID, id);
        super.addToMap();
    }
}
