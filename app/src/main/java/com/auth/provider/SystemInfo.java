package com.auth.provider;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import android.os.SystemProperties;

public class SystemInfo {

    private final String TAG = "SystemInfo";
    //机顶盒ID 32位
    public final static String KEY_STBID = "STBID";
    //机顶盒型号
    public final static String KEY_STBTYPE = "STBType";
    //机顶盒软件版本
    public final static String KEY_SOFTWARE_VERSION = "SoftwareVersion";
    //机顶盒IP
    public final static String KEY_IP = "ip";
    //机顶盒有线mac
    public final static String KEY_MAC = "MAC";
    //设备序号 15位
    public final static String KEY_DEVICE_ID = "DeviceID";
    //设备播放编码 15位，广电串号;出厂为空
    public final static String KEY_DC = "DeviceCode";
    //业务账户
    public final static String KEY_USER_ID = "UserID";
    //用户token
    public final static String KEY_TOKEN = "UserToken";
    //业务调度的EPG地址，格式：http://ip:port/xxx
    public final static String KEY_EPG_URL = "EPGServerURL";
    //最后一次播放的频道号
    public final static String KEY_LCN = "LastchannelNum";
    //盒子供应商标识
    public final static String KEY_VENDOR = "Vendor";
    //1，支持播放器多实例，0不支持播放器多实例
    public final static String KEY_MP = "SupportMultiPlayer";
    //对接平台标示
    public final static String KEY_RESERVE = "Reserved";
    public final static String PROP_MAC = "ro.mac";
    public final static String PROP_STBID ="ro.tystbid";
    public final static String PROP_VERSION = "ro.stb.version";
    public final static String PROP_REMOTE_SERVER = "ro.remoteserver.ip";
    public final static String PROP_TYPE = "ro.product.type";
    public final static String PROP_DEVICEID = "ro.stb.device.id";
    public final static String PROP_DEVICE_CODE = "ro.device.code";
    public final static String PROP_MULTIPLAY_SUPPORT = "ro.product.multiPlayer";


    //服务器地址
    public final static String KEY_REMOTE_SERVER_HW = "remote_server";

    private static Map<String, String> dataMap = new HashMap<String, String>();

    private static Handler mDataAccessServiceHander = null;

    private static SystemInfo mInstance = null;
    //for test


    private SystemInfo(){
        updateSystemInfo();
    }

    public static SystemInfo getInstance(Context context){
        if (mInstance == null){
            mInstance = new SystemInfo();
        }
//        mDataAccessServiceHander = handler;
        return mInstance;
    }

    public static SystemInfo getInstance(){
        if(mInstance == null){
            mInstance = new SystemInfo();
        }
        return mInstance;
    }

    public String getSystemInfor(String key, String def){
        String ret = def;
        if (dataMap.containsKey(key)){
            ret = dataMap.get(key);
        }
        return ret;
    }

    private void updateSystemInfo(){

        dataMap.put(KEY_STBID, systemPropGet(PROP_STBID, "default"));
        dataMap.put(KEY_STBTYPE, Build.MODEL);
        dataMap.put(KEY_SOFTWARE_VERSION, systemPropGet(PROP_VERSION,"default"));
        dataMap.put(KEY_MAC, systemPropGet(PROP_MAC,"default"));
        dataMap.put(KEY_VENDOR, Build.MANUFACTURER);
        dataMap.put(KEY_MP, systemPropGet(PROP_MULTIPLAY_SUPPORT, "default"));
        //测试地址
        dataMap.put(KEY_REMOTE_SERVER_HW, systemPropGet(PROP_REMOTE_SERVER, "localhost"));
        //正式地址
//        dataMap.put(KEY_REMOTE_SERVER_HW,"10.100.0.52:58009");

    }

    private String systemPropGet(String key, String def){
        return SystemProperties.get(key, def);
    }

    public boolean setSystemInfo(String key, String val){
        if(key.isEmpty() || val.isEmpty()){
            Log.e(TAG, "value invalidate");
            return false;
        }
        dataMap.put(key, val);
        return true;
    }

    public boolean containsKey(String key){
        return dataMap.containsKey(key);
    }
    //do nothing right now
    public void getRemoteServerAddr(){
        mDataAccessServiceHander.sendEmptyMessage(DataAccessService.ACTION_GET_REMOTE_SERVER_START);
        mDataAccessServiceHander.sendEmptyMessage(DataAccessService.ACTION_GET_REMOTE_SERVER_DONE);
    }

}
