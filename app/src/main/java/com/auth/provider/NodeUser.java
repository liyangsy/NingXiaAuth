package com.auth.provider;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class NodeUser extends NodeBase {
    private static final String TAG = "NodeUser";
    public static String ID = "id";
    public static String NAME = "name";
    public static String PASSWORD = "password";
    public static String RANK = "rank";
    public static String EMAIL = "email";
    public static String TELEPHONE = "telephone";
    public static String ADDR = "addr";
    public static String AGE = "age";
    public static String SEX = "sex";
    public static String OCCUPATION = "occupation";
    public static String DEVICE_ID = "device_id";
    public static String HEADIMGURL = "headimgurl";
    public static String FIRST_USE_TIME = "first_use_time";
    public static String ATTRIBUTION = "attribution";
    public static String USER_FROM = "user_from";
    public static String USER_LEVEL = "user_level";
    public static String BOSS_USER_GROUP = "boss_user_group";
    public static String USER_VIP_LEVEL_DETAIL_INFO = "user_vip_level_detail_info";
    public static String USER_GROWTH_VALUE = "user_growth_value";
    public static String USER_LEVEL_BEGIN_TIME = "user_level_begin_time";
    public static String USER_LEVEL_END_TIME = "user_level_end_time";
    public static String BOSS_TOP_BOX_ID = "boss_top_box_id";
    public static String USER_IS_CATEGORY = "user_is_category";
    public static String BOSS_AREA_CODE = "boss_area_code";
    public static String BOSS_AREA_BIND = "boss_area_bind";
    public static String AREA_CODE = "area_code";
    public static String NNS_LAST_LOGIN_VERSION = "nns_last_login_version";
    public static String NNS_LAST_LOGIN_MAC = "nns_last_login_mac";

    public NodeUserVipLevelDetailInfo mNodeUserVipLevelDetailInfo = new NodeUserVipLevelDetailInfo();

    public String id = null;
    public String name = null;
    public String password = null;
    public String rank = null;
    public String email = null;
    public String telephone = null;
    public String addr = null;
    public String age = null;
    public String sex = null;
    public String occupation = null;
    public String device_id = null;
    public String headimgurl = null;
    public String first_use_time = null;
    public String attribution = null;
    public String user_from = null;
    public String user_level = null;
    public String boss_user_group = null;
//    public String user_vip_level_detail_info = null;
    public String user_growth_value = null;
    public String user_level_begin_time = null;
    public String user_level_end_time = null;
    public String boss_top_box_id = null;
    public String user_is_category = null;
    public String boss_area_code = null;
    public String boss_area_bind = null;
    public String area_code = null;
    public String nns_last_login_version = null;
    public String nns_last_login_mac = null;

    NodeUser() {
        super(TAG);
    }

    @Override
    public void updateNodeInfo(JSONObject obj) throws JSONException {
        Log.d(TAG, "update User node");
        id = obj.getString(NodeUser.ID);
        name = obj.getString(NodeUser.NAME);
        rank = obj.getString(NodeUser.RANK);
        email = obj.getString(NodeUser.EMAIL);
        telephone = obj.getString(NodeUser.TELEPHONE);
        addr = obj.getString(NodeUser.ADDR);
        age = obj.getString(NodeUser.AGE);
        sex = obj.getString(NodeUser.SEX);
        occupation = obj.getString(NodeUser.OCCUPATION);
        device_id = obj.getString(NodeUser.DEVICE_ID);
        headimgurl = obj.getString(NodeUser.HEADIMGURL);
        first_use_time = obj.getString(NodeUser.FIRST_USE_TIME);
        attribution = obj.getString(NodeUser.ATTRIBUTION);
        user_from = obj.getString(NodeUser.USER_FROM);
        user_level = obj.getString(NodeUser.USER_LEVEL);
        boss_user_group = obj.getString(NodeUser.BOSS_USER_GROUP);
        // no deal with user_vip_level_detail_info right now
//        JSONObject temp = obj.getJSONObject(NodeUser.USER_VIP_LEVEL_DETAIL_INFO);
//        mNodeUserVipLevelDetailInfo.updateNodeInfo(temp);
        user_growth_value = obj.getString(NodeUser.USER_GROWTH_VALUE);
//        user_level_begin_time = obj.getString(NodeUser.USER_LEVEL_BEGIN_TIME);
//        user_level_end_time = obj.getString(NodeUser.USER_LEVEL_END_TIME);
        boss_top_box_id = obj.getString(NodeUser.BOSS_TOP_BOX_ID);
        user_is_category = obj.getString(NodeUser.USER_IS_CATEGORY);
        boss_area_code = obj.getString(NodeUser.BOSS_AREA_CODE);
        boss_area_bind = obj.getString(NodeUser.BOSS_AREA_BIND);
        area_code = obj.getString(NodeUser.AREA_CODE);
        nns_last_login_version = obj.getString(NodeUser.NNS_LAST_LOGIN_VERSION);
        nns_last_login_mac = obj.getString(NodeUser.NNS_LAST_LOGIN_MAC);

        addToMap();
    }

    @Override
    void addToMap() {
        mMap.put(ID, id);
        mMap.put(NAME, name);
        mMap.put(RANK, rank);
        mMap.put(EMAIL, email);
        mMap.put(TELEPHONE, telephone);
        mMap.put(ADDR, addr);
        mMap.put(AGE, age);
        mMap.put(SEX, sex);
        mMap.put(OCCUPATION, occupation);
        mMap.put(DEVICE_ID, device_id);
        mMap.put(HEADIMGURL, headimgurl);
        mMap.put(FIRST_USE_TIME, first_use_time);
        mMap.put(ATTRIBUTION, attribution);
        mMap.put(USER_FROM, user_from);
        mMap.put(USER_LEVEL, user_level);
        mMap.put(BOSS_USER_GROUP, boss_user_group);
        mMap.put(USER_GROWTH_VALUE, user_growth_value);
        mMap.put(USER_LEVEL_BEGIN_TIME, user_level_begin_time);
        mMap.put(USER_LEVEL_END_TIME, user_level_end_time);
        mMap.put(BOSS_TOP_BOX_ID, boss_top_box_id);
        mMap.put(USER_IS_CATEGORY, user_is_category);
        mMap.put(BOSS_AREA_CODE, boss_area_code);
        mMap.put(BOSS_AREA_BIND, boss_area_bind);
        mMap.put(AREA_CODE, area_code);
        mMap.put(NNS_LAST_LOGIN_VERSION, nns_last_login_version);
        mMap.put(NNS_LAST_LOGIN_MAC, nns_last_login_mac);
        super.addToMap();
    }

    public String getUserId(){
        return getItem(ID);
    }


    @Override
    public String getItem(String key) {
        return mMap.get(key);
    }

    @Override
    void dump(StringBuffer sb) {
        sb.append(TAG).append("\r\n");
        Iterator it = mMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            sb.append(key).append("=").append(val).append("\r\n");
        }

        mNodeUserVipLevelDetailInfo.dump(sb);
    }
}
