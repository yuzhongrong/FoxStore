package com.android.mobchat.app;

import com.android.mobchat.glide.SimpleMobIMMessageReceiver;
import com.android.mobchat.model.MsgReceiverListener;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.mob.imsdk.MobIM;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * Created by yuzhongrong on 2018/8/11.
 */

public class FoxChatApplication extends BaseApplication {

    private SimpleMobIMMessageReceiver mobMsgRever = null;



    public void regMsgRev() {
        if (mobMsgRever == null) {
            mobMsgRever = new SimpleMobIMMessageReceiver(this);
            MobIM.addMessageReceiver(mobMsgRever);
        }
    }

    public void onTerminate() {
        super.onTerminate();
        if (mobMsgRever != null) {
            MobIM.removeMessageReceiver(mobMsgRever);
        }
        mobMsgRever = null;
    }

    public void addMsgRever(MsgReceiverListener listener) {
        mobMsgRever.addMsgRever(listener);
    }

    public void removeMsgRever(MsgReceiverListener listener) {
        mobMsgRever.removeMsgRever(listener);
    }

    public void addGroupMsgRever(MsgReceiverListener listener) {
        mobMsgRever.addGroupMsgRever(listener);
    }

    public void removeGroupMsgRever(MsgReceiverListener listener) {
        mobMsgRever.removeGroupMsgRever(listener);
    }



}
