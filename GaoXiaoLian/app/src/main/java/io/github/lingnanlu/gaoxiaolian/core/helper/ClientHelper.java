package io.github.lingnanlu.gaoxiaolian.core.helper;

import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import io.github.lingnanlu.gaoxiaolian.core.CallBack;

/**
 * Created by rico on 5/16/2016.
 */
public class ClientHelper {

    private static final String TAG = "ClientHelper";
    private static ClientHelper instance;
    private String clientId;
    private AVIMClient client;

    public static ClientHelper getInstance() {
        if (instance == null) {
            instance = new ClientHelper();
        }

        return instance;
    }

    private ClientHelper(){}

    public String getClientId() {
        return clientId;
    }

    public void open(String clientId, final CallBack<Void> cb) {
        this.clientId = clientId;
        client = AVIMClient.getInstance(clientId);
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "done: open client success ");
                    cb.onResult(null);
                } else {
                    Log.d(TAG, "done: open client failed ");
                    cb.onError(e);
                }
            }
        });
    }

    public AVIMClient getClient() {
        return client;
    }
}
