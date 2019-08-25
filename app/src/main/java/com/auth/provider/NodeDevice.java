package com.auth.provider;

import org.json.JSONException;
import org.json.JSONObject;

public class NodeDevice implements NodeBase{
    public static String ID = "id";
    public static String NAME = "name";
    public static String FIRST_USE_TIME = "first_use_time";
    public static String CREATE_TIME = "create_time";
    public static String IS_ADD_DEVICE = "is_add_device";


    public String id = null;
    public String name = null;
    public String first_use_time = null;
    public String create_time = null;
    public String is_add_device = null;

    @Override
    public void updateNodeInfo(JSONObject obj) throws JSONException {
        id = obj.getString(NodeDevice.ID);
        name = obj.getString(NodeDevice.NAME);
        first_use_time = obj.getString(NodeDevice.FIRST_USE_TIME);
        create_time = obj.getString(NodeDevice.FIRST_USE_TIME);
        is_add_device = obj.getString(NodeDevice.IS_ADD_DEVICE);
    }
}
