package foxstore.android.com.foxstore.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.PhoneNumberUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.BaseView;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.kes.AcacheKeys;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.bean.User;
import foxstore.android.com.foxstore.widgets.CusEditText;
@Route(path = ActivityKeys.ACTIVITY_LOGIN,name = "登录页面")
public class LoginActivity extends BaseFoxStoreActivity {
//    private ImageView back;
    private TextView title;
    private  EventHandler eh;
    private TextView register;
    private TextView sendcode;
    private CusEditText phone;
    private CusEditText code;
    private CusEditText pwd;
    private Button login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
    //    back=findViewById(R.id.back);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        phone=findViewById(R.id.phone);
        pwd=findViewById(R.id.pwd);
        initDrawable();



    }

    private void initDrawable() {
        Drawable drawableaccount=getResources().getDrawable(R.drawable.regist_account);
        drawableaccount.setBounds(0,0,50,50);
        phone.setCompoundDrawables(drawableaccount,null,null,null);
        Drawable drawable_pwd=getResources().getDrawable(R.drawable.regist_pwd);
        drawable_pwd.setBounds(0,0,50,50);
        pwd.setCompoundDrawables(drawable_pwd,null,null,null);

    }




    @Override
    protected void onEvent() {
    //    back.setOnClickListener(v -> finish());
        login.setOnClickListener(v -> {
            filterHandler();

        });
        register.setOnClickListener(v -> {ARouter.getInstance().build( ActivityKeys.ACTIVITY_REGISTER).navigation();
        });
    }

    //注册前过滤操作
    private void filterHandler() {
            String phonestr=phone.getText().toString();
        String pwdstr=pwd.getText().toString();

        if(TextUtils.isEmpty(phonestr)){
            ToastUtil.show("请输入手机号码！");
            return;
        }else if(TextUtils.isEmpty(pwdstr)){
            ToastUtil.show("请输入密码！");
            return;
        }

        LoginAccount(phonestr,pwdstr);
    }





    /**登录帐号*/
    private void LoginAccount(String phone,String pwd){
        //调用bmob 创建一个帐号
        showDialog();
       User user=new User();
        user.setUsername(phone);
        user.setPassword(pwd);
         user.login(new SaveListener<User>() {

             @Override
             public void done(User user, BmobException e) {
                 if(e ==null){
                      ToastUtil.show("登录成功");
                      ACacheUtil.get(LoginActivity.this).put(AcacheKeys.LOGINBEAN,user);
                     XGPushManager.registerPush(LoginActivity.this, user.getUsername(), new XGIOperateCallback() {
                         @Override
                         public void onSuccess(Object o, int i) {
                             LogUtil.d("------信鸽帐号绑定成功------->"+o.toString());
                         }

                         @Override
                         public void onFail(Object o, int i, String s) {
                             LogUtil.d("------信鸽帐号绑定失败------->"+s);

                         }
                     });

                      finish();
                     ARouter.getInstance().build( ActivityKeys.ACTIVITY_MAIN).navigation();//进入主页

                 }else{
                     if(e.getErrorCode()==101){
                         ToastUtil.show("用户名和密码不匹配");

                     }else{
                         ToastUtil.show("登录失败");
                     }


                 }

                 dismissDialog();
             }
         });

    }


    @Override
    protected void onStop() {
        super.onStop();
        if(eh!=null)eh.onUnregister();
    }

    @Override
    protected BaseView getView() {
        return null;
    }


    /**
     * 点击手机返回键finish app
     */
    @Override
    public void onBackPressed() {
        exitApp();
    }


}
