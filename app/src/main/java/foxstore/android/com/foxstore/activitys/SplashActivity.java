package foxstore.android.com.foxstore.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.RxTimerUtil;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;

import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.kes.AcacheKeys;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.bean.User;

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        RxTimerUtil.timer(3,number -> Arouter2TargetActivity());
    }

    @Override
    protected void onEvent() {
    }

    @Override
    protected BaseView getView() {
        return null;
    }

    private void Arouter2TargetActivity(){
        User user= (User) ACacheUtil.get(SplashActivity.this).getAsObject(AcacheKeys.LOGINBEAN);
        if(user!=null){//登录过的直接进入主页
            ARouter.getInstance().build(ActivityKeys.ACTIVITY_MAIN).navigation();
        }else{//进入登录页
            ARouter.getInstance().build( ActivityKeys.ACTIVITY_LOGIN).navigation();
        }
        this.finish();

    }
}
