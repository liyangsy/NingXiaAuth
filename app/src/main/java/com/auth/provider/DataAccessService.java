package com.auth.provider;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.thirdparty.dataaccess.IDataAccess;


public class DataAccessService extends Service {
    private static final String TAG = "DataAccessService";

    public final static int ACTION_NET_CONNECTED = 1;

    public final static int ACTION_DEVICE_AUTH_STARTED = 5;
    public final static int ACTION_DEVICE_AUTH_DONE = 6;
    public final static int RESULT_DEVICE_AUTH_SUCCESS =7;
    public final static int RESULT_DEVICE_AUTH_FAILED =8;
    public final static int ACTION_TOKEN_AUTH_STARTED = 10;
    public final static int ACTION_TOKEN_AUTH_DONE = 11;
    public final static int RESULT_TOKEN_AUTH_SUCCESS =12;
    public final static int RESULT_TOKEN_AUTH_FAILED =13;
    public final static int ACTION_TOKEN_REFRESH_STARED = 15;
    public final static int ACTION_TOKEN_REFRESH_DONE = 16;
    public final static int RESULT_TOKEN_REFRESH_SUCCESS =17;
    public final static int RESULT_TOKEN_REFRESH_FAILED =18;

    public final static int ACTION_GET_REMOTE_SERVER_START = 20;
    public final static int ACTION_GET_REMOTE_SERVER_DONE =21;

    public final static int ACTION_REFREASH_TIME_CHECK = 25;
    public final static int ACTION_REFRESH_TOKEN = 26;
    public final static int ACTION_REAUTH_DEVICE = 27;


    private static final String SP = "data";

    private DataAccessServiceBinder binder;
    private BroadcastReceiver mBroadcastReceiver = null;
    private IntentFilter mNetStateFilter = null;

    private Context mContext = null;
    private SystemInfo mSysInfo = null;
    private AuthManager mAuthMananger =null;
    private Handler mHandler = null;



    public DataAccessService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service onCreate");
        mContext = this.getApplicationContext();
        mSysInfo = SystemInfo.getInstance(mContext);
        mAuthMananger = new AuthManager(this);
        initBroadcastReceiver();

        mHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG,"Got msg: " + toString(msg.what));
                switch (msg.what){
                    case ACTION_GET_REMOTE_SERVER_START:
                        break;
                    case ACTION_GET_REMOTE_SERVER_DONE:
                        mAuthMananger.startDeviceAuth();
                        break;
                    case ACTION_NET_CONNECTED:
                    case ACTION_REAUTH_DEVICE:
                        //net connected then should start device auth
                        //mAuthMananger.startDeviceAuth();
//                        mSysInfo.getRemoteServerAddr();

                        mAuthMananger.startDeviceAuth();
                        break;
                    case ACTION_DEVICE_AUTH_STARTED:
                        break;
                    case ACTION_DEVICE_AUTH_DONE:

                        mAuthMananger.startTokenAuth();
                        break;
                    case ACTION_REFRESH_TOKEN:
                        mAuthMananger.startTokenFresh();;
                    case ACTION_TOKEN_AUTH_STARTED:
                        break;
                    case ACTION_TOKEN_AUTH_DONE:
                        mAuthMananger.storeInfoToSettings();
                        sendEmptyMessage(ACTION_REFREASH_TIME_CHECK);
                        break;
                    case ACTION_TOKEN_REFRESH_STARED:
                        break;
                    case ACTION_TOKEN_REFRESH_DONE:
//                        break;
                    case ACTION_REFREASH_TIME_CHECK:

                        mAuthMananger.checkAndSetRefreshTime();
                        if(mReAuth){
                            sendEmptyMessageDelayed(ACTION_REAUTH_DEVICE, mDelay * 1000);
                        }else {
                            sendEmptyMessageDelayed(ACTION_REFRESH_TOKEN,  mDelay * 1000);
                        }

                        break;
                        default:
                            Log.w(TAG, "Not handled!");
                            break;
                }
                super.handleMessage(msg);
            }

            private  String toString(int what) {
                String ret = null;
                switch (what) {
                    case ACTION_NET_CONNECTED:
                        ret = "ACTION_NET_CONNECTED";
                        break;
                    case ACTION_DEVICE_AUTH_STARTED:
                        ret = "ACTION_DEVICE_AUTH_STARTED";
                        break;
                    case ACTION_DEVICE_AUTH_DONE:
                        ret = "ACTION_DEVICE_AUTH_DONE";
                        break;
                    case ACTION_TOKEN_AUTH_STARTED:
                        ret = "ACTION_TOKEN_AUTH_STARTED";
                        break;
                    case ACTION_TOKEN_AUTH_DONE:
                        ret = "ACTION_TOKEN_AUTH_DONE";
                        break;
                    case ACTION_TOKEN_REFRESH_STARED:
                        ret = "ACTION_TOKEN_REFRESH_STARED";
                        break;
                    case ACTION_TOKEN_REFRESH_DONE:
                        ret = "ACTION_TOKEN_REFRESH_DONE";
                        break;
                    default:
                        ret = "not support";
                        break;

                }
                return ret;
            }
        };
        this.getApplicationContext().registerReceiver(mBroadcastReceiver, mNetStateFilter);

    }

    public void sendEmptyMessage(int what){
        mHandler.sendEmptyMessage(what);
    }

    public void sendMessage(Message msg){
        mHandler.sendMessage(msg);
    }
    /*
    * delay 代表下次fresh间隔
    * reAuth：true重新进行设备认证，false只需freshtoken即可
    * */
    private int mDelay = 0;
    private boolean mReAuth = false;
    public void setRefreshTokenTime(int delay, boolean reAuth){
        mDelay = delay;
        mReAuth = reAuth;
    }


    public void initBroadcastReceiver(){
        binder = new DataAccessServiceBinder();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                    if(intent.hasExtra(ConnectivityManager.EXTRA_NETWORK_INFO)){
                        NetworkInfo mNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                        if (mNetworkInfo.isConnected()){
                            Log.d(TAG, "Network is ok, then begin to device auth");
                            mHandler.sendEmptyMessage(ACTION_NET_CONNECTED);
                        }
                    }
                }
            }
        };
        mNetStateFilter = new IntentFilter();
        mNetStateFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "get a client");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private class DataAccessServiceBinder extends IDataAccess.Stub{


        @Override
        public String getSTBData(String dataName, String extData) throws RemoteException {
            Log.d(TAG, "get STB date: " + dataName);
            String ret = null;
            if(TextUtils.isEmpty(dataName)) {
                Log.e(TAG, "key word is empty!");
                return ret;
            }

            if(mSysInfo.containsKey(dataName)){
                ret = mSysInfo.getSystemInfor(dataName, null);
            }else{
                Log.d(TAG,"No data in map, get from database");
                ret = Settings.Global.getString(mContext.getContentResolver(), dataName);
            }
            Log.d(TAG, "get STB data key: " + dataName + ":" + ret);

            return ret;
        }

        @Override
        public int setSTBData(String dataName, String value, String extData) throws RemoteException {
            if(TextUtils.isEmpty(dataName)){
                Log.w(TAG, "Key word is empty, please set right key");
                return 0;
            }

            mSysInfo.setSystemInfo(dataName, value);
            Settings.Global.putString(mContext.getContentResolver(), dataName, value);
/*            SharedPreferences sp = getSharedPreferences(SP, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(dataName, value);
            ed.commit();*/
            return 1;

        }

    }

    public void storeInfoToSettings(NodeAuth auth, NodeUser user){
        String token = auth.getWebToken();
        String id = user.getUserId();
        Settings.Global.putString(mContext.getContentResolver(),SystemInfo.KEY_USER_ID, id);
        Settings.Global.putString(mContext.getContentResolver(),SystemInfo.KEY_TOKEN, token);
    }
}
