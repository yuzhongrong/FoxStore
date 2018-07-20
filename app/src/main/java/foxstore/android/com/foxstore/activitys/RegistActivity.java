package foxstore.android.com.foxstore.activitys;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cjwsc.idcm.Utils.JsonUtil;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.PhoneNumberUtil;
import com.cjwsc.idcm.Utils.RxTimerUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.BaseView;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.bean.User;
import foxstore.android.com.foxstore.bean.MobMessageError;
import foxstore.android.com.foxstore.utils.BmobUtils;
import foxstore.android.com.foxstore.widgets.CusEditText;

@Route(path = ActivityKeys.ACTIVITY_REGISTER)
public class RegistActivity extends BaseFoxStoreActivity {
    private ImageView back;
    private TextView title;
    private  EventHandler eh;
    private Button register;
    private TextView sendcode;
    private CusEditText phone;
    private CusEditText code;
    private CusEditText pwd;
    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    CreateAccount(phone.getText().toString(),pwd.getText().toString());
                }else if (msg.arg1 == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功后开始60s倒计时
                    RxTimerUtil.startTime(RegistActivity.this, sendcode, "剩余", 60, new RxTimerUtil.IRxNext() {
                        @Override
                        public void doNext(long number) {
                            sendcode.setText("获取验证码");
                        }
                    });
                }else if (msg.arg1 ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                LogUtil.d("---------发信发送失败----->"+((Throwable) msg.obj).getMessage());
                MobMessageError error=  JsonUtil.toBean(((Throwable) msg.obj).getMessage(), MobMessageError.class);
                ToastUtil.show(error.getDescription());
             //   ToastUtil.show("无效的短信验证码");
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        back=findViewById(R.id.back);
        title=findViewById(R.id.title);
        register=findViewById(R.id.register);
        sendcode=findViewById(R.id.sendcode);
        phone=findViewById(R.id.phone);
        code=findViewById(R.id.code);
        pwd=findViewById(R.id.pwd);
        title.setText("新用户注册");
        initDrawable();
        initMobSms();



    }

    private void initDrawable() {
        Drawable drawableaccount=getResources().getDrawable(R.drawable.regist_account);
        drawableaccount.setBounds(0,0,50,50);
        phone.setCompoundDrawables(drawableaccount,null,null,null);

        Drawable drawable_code=getResources().getDrawable(R.drawable.regist_mail);
        drawable_code.setBounds(0,0,45,45);//图片大小和其他的不一样所以这里设置才不一样
        code.setCompoundDrawables(drawable_code,null,null,null);

        Drawable drawable_pwd=getResources().getDrawable(R.drawable.regist_pwd);
        drawable_pwd.setBounds(0,0,50,50);
        pwd.setCompoundDrawables(drawable_pwd,null,null,null);

    }

    private void initMobSms() {
         eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
         };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }




    @Override
    protected void onEvent() {
        back.setOnClickListener(v -> finish());
        //发送验证码
        sendcode.setOnClickListener(v -> {
            if(TextUtils.isEmpty(phone.getText().toString())){
                ToastUtil.show("手机号码不能为空！");
                return;
            }else if(!PhoneNumberUtil.isMobileNO(phone.getText().toString())){

                ToastUtil.show("请输入正确的手机号码！");
                return;
            }

            SMSSDK.getVerificationCode("86",phone.getText().toString());});
        register.setOnClickListener(v -> {
            filterHandler();

        });
    }

    //注册前过滤操作
    private void filterHandler() {
            String phonestr=phone.getText().toString();
        String codestr=code.getText().toString();
        String pwdstr=pwd.getText().toString();

        if(TextUtils.isEmpty(phonestr)){
            ToastUtil.show("请输入手机号码！");
            return;
        }else if(TextUtils.isEmpty(codestr)){
            ToastUtil.show("请输入验证码！");
            return;
        }else if(TextUtils.isEmpty(pwdstr)){
            ToastUtil.show("请输入密码！");
            return;
        }

        checkCodeisOk(phonestr,codestr);

    }




    /**检测验证码的正确性*/
    private void checkCodeisOk(String phone,String code) {
        //注册帐号前要先验证 验证码的正确性
        SMSSDK.submitVerificationCode("86",phone,code);

    }

    private void CreateAccount(String phone,String pwd){
        //调用bmob 创建一个帐号
        User user=new User();
        user.setUsername(phone);
        user.setPassword(pwd);
        user.setHeadimg("http://bmob-cdn-12859.b0.upaiyun.com/2018/07/20/0c7fc36e400be2e08074d9627340931b.png");//创建帐号的时候给个默认头像
        BmobUtils.insertObject(this, user, new BmobUtils.CallBack() {
            @Override
            public void onSuccess(String s) {
                ToastUtil.show("注册成功!");
                finish();
//                ARouter.getInstance().build( ActivityKeys.ACTIVITY_LOGIN).navigation();//注册也必然是登录页面跳进来的
            }

            @Override
            public void onError(String errorcode) {
                if(errorcode.equals("202")){
                    ToastUtil.show("用户已经注册");

                }else{
                    ToastUtil.show("注册失败,");
                }


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




}
