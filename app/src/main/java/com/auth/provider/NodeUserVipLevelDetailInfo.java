package com.auth.provider;

import org.json.JSONException;
import org.json.JSONObject;

public class NodeUserVipLevelDetailInfo implements NodeBase {
    public static String NNS_ID = "nns_id";
    public static String NNS_VIP_LEVEL = "nns_vip_level";
    public static String NNS_STATUS = "nns_status";
    public static String NNS_IS_HAVE_AD = "nns_is_have_advertisement";
    public static String NNS_IMG_V = "nns_img_v";
    public static String NNS_IMG_H = "nns_img_h";
    public static String NNS_IMG_S = "nns_img_s";
    public static String NNS_DESC = "nns_desc";
    public static String NNS_EXTEND_LANGUAGE = "nns_extend_language";
    public static String NNS_CREATE_TIME = "nns_create_time";
    public static String NNS_MODIFY_TIME = "nns_modify_time";

    public String nns_id = null;
    public String nns_vip_level = null;
    public String nns_status = null;
    public String nns_is_have_ad = null;
    public String nns_img_v = null;
    public String nns_img_h = null;
    public String nns_img_s = null;
    public String nns_desc = null;
    public String nns_extend_language = null;
    public String nns_create_time = null;
    public String nns_modify_time = null;

    @Override
    public void updateNodeInfo(JSONObject obj) throws JSONException {
        nns_id = obj.getString(NodeUserVipLevelDetailInfo.NNS_ID);
        nns_vip_level = obj.getString(NodeUserVipLevelDetailInfo.NNS_VIP_LEVEL);
        nns_status = obj.getString(NodeUserVipLevelDetailInfo.NNS_STATUS);
        nns_is_have_ad = obj.getString(NodeUserVipLevelDetailInfo.NNS_IS_HAVE_AD);
        nns_img_v = obj.getString(NodeUserVipLevelDetailInfo.NNS_IMG_V);
        nns_img_h = obj.getString(NodeUserVipLevelDetailInfo.NNS_IMG_H);
        nns_img_s = obj.getString(NodeUserVipLevelDetailInfo.NNS_IMG_S);
        nns_desc = obj.getString(NodeUserVipLevelDetailInfo.NNS_DESC);
        nns_extend_language = obj.getString(NodeUserVipLevelDetailInfo.NNS_EXTEND_LANGUAGE);
        nns_create_time = obj.getString(NodeUserVipLevelDetailInfo.NNS_CREATE_TIME);
        nns_modify_time = obj.getString(NodeUserVipLevelDetailInfo.NNS_MODIFY_TIME);
    }
}
