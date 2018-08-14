package foxstore.android.com.foxstore.app;
import android.content.Context;

import com.android.mobchat.app.FoxChatApplication;
import com.android.mobchat.glide.SimpleMobIMMessageReceiver;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.TakePhotoUtils;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.Utils.sound.SoundPlayUtils;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.mob.MobSDK;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.interceptors.AccessTokenInterceptor;
import okhttp3.Interceptor;


/**
 * Created by yuzhongrong on 2018/5/24.
 */

public class FoxStorApplication extends FoxChatApplication {



    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                // layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色

                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//.setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);

            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        initBmob();
        initTake();
        initXG();
        imitMob();
        SoundPlayUtils.init(this);
    }

    private void imitMob() {
        MobSDK.init(this);
    }


    private void initXG() {

        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                LogUtil.d("信鸽设备注册成功"+o.toString());
            }

            @Override
            public void onFail(Object o, int i, String s) {
                LogUtil.d("信鸽设备注册失败"+s);
            }
        });
    }

    private void initBmob() {

        //第一：默认初始化
        Bmob.initialize(this, "6bac156aa05cd64c706c018d91d191ad");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);

    }

    private void  initTake(){

        TakePhotoUtils.init(R.layout.dialog_photo_choose, () -> ToastUtil.show(getString(R.string.permission_deny)));

    }


    @Override
    public List<Interceptor> getItercepors() {
        List<Interceptor> temp = new ArrayList<>();
        temp.add(new AccessTokenInterceptor());
        return temp;
    }



}
