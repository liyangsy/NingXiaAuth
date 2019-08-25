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


    private static final String SP = "data";

    private DataAccessServiceBinder binder;
    private BroadcastReceiver mBroadcastReceiver = null;
    private IntentFilter mNetStateFilter = null;
    private Handler mHandler = null;
    private Context mContext = null;
    private SystemInfo mSysInfo = null;



    public DataAccessService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service onCreate");
        mContext = this.getApplicationContext();
        mSysInfo = SystemInfo.getInstance(mContext);
        initBroadcastReceiver();
        final AuthManager mAuthMananger = new AuthManager(mContext, null, mHandler);
        mHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG,"Got msg: " + msg.what);
                switch (msg.what){
                    case ACTION_GET_REMOTE_SERVER_START:
                        break;
                    case ACTION_GET_REMOTE_SERVER_DONE:
                        mAuthMananger.startDeviceAuth();
                        break;
                    case ACTION_NET_CONNECTED:
                        //net connected then should start device auth
                        //mAuthMananger.startDeviceAuth();
                        mSysInfo.getRemoteServerAddr();
                        break;
                    case ACTION_DEVICE_AUTH_STARTED:
                        break;
                    case ACTION_DEVICE_AUTH_DONE:
                        mAuthMananger.startTokenAuth();
                        break;
                    case ACTION_TOKEN_AUTH_STARTED:
                        break;
                    case ACTION_TOKEN_AUTH_DONE:
                        break;
                    case ACTION_TOKEN_REFRESH_STARED:
                        break;
                    case ACTION_TOKEN_REFRESH_DONE:
                        break;
                        default:
                            Log.w(TAG, "Not handled!");
                            break;
                }
                super.handleMessage(msg);
            }
        };
        this.getApplicationContext().registerReceiver(mBroadcastReceiver, mNetStateFilter);

    }

    public void initBroadcastReceiver(){
        binder = new DataAccessServiceBinder();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                    if(intent.hasExtra(ConnectivityManager.EXTRA_NETWORK_INFO)){
                        NetworkInfo mNetworkInfo = (NetworkInfo)intent.getExtras(ConnectivityManager.EXTRA_NETWORK_INFO);
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
                SharedPreferences sp = getSharedPreferences(SP, Context.MODE_PRIVATE);
                ret = sp.getString(dataName, null);
            }

            return ret;
        }

        @Override
        public int setSTBData(String dataName, String value, String extData) throws RemoteException {
            if(TextUtils.isEmpty(dataName)){
                Log.w(TAG, "Key word is empty, please set right key");
                return 0;
            }

            mSysInfo.setSystemInfo(dataName, value);

            SharedPreferences sp = getSharedPreferences(SP, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(dataName, value);
            ed.commit();
            return 1;

        }

    }
}
