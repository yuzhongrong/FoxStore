package foxstore.android.com.foxstore.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.JsonUtil;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.net.callback.RxProgressSubscriber;
import com.cjwsc.idcm.net.exception.ResponseThrowable;

import butterknife.OnClick;
import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.kes.AcacheKeys;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.iprovider.DuoDuoAuth2ProviderServices;
import foxstore.android.com.common.bean.AccessTokenBean;
import foxstore.android.com.foxstore.model.params.ReqAccessTokenParam;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
import static foxstore.android.com.foxstore.api.DuoDuoHttpApi.auth_url1;

/**
 * Created by hpz on 2018/1/15.
 */
@Route(path = ActivityKeys.ACTIVITY_AUTHWEB)
public class WebViewActivity extends BaseFoxStoreActivity {


    LinearLayout imgBack;
    TextView tvSetName;
    WebView mWebView;
    ProgressBar progressBar1;
   // private String coinUrl = "";
    private String title = "";
    @Autowired
    DuoDuoAuth2ProviderServices duoDuoAuth2ProviderServices;
    @Autowired(name = "url")
    String coinUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        ARouter.getInstance().inject(this);
        tvSetName=findViewById(R.id.tv_set_Name);
        mWebView= findViewById(R.id.webview);
        imgBack= findViewById(R.id.mr_back_layout);
        progressBar1= findViewById(R.id.progressBar1);
        Intent intent = getIntent();
        Bundle bundle1 = intent.getExtras();
        coinUrl = bundle1.getString("url");
        title = bundle1.getString("title");
        tvSetName.setText(title);
    }

    @Override
    protected void onEvent() {
        tvSetName.setText("");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.equals("https://mms.pinduoduo.com/Pdd.html?from=mmt#/index?pre=log_page")||url.equals("http://mms.pinduoduo.com/guide/traffic-guidance")){//登录成功后直接重新加载授权url
                    view.loadUrl(auth_url1);

                }else if(url.contains("code=")){//点击确认授权直接拿code
                    LogUtil.d("成功获取code");
                    /**请求assets_token*/
                   String code= url.substring(url.indexOf("=")+1);
                    duoDuoAuth2ProviderServices.auth2(new ReqAccessTokenParam("authorization_code",code))
                            .compose(bindToLifecycle())
                            .subscribe(new RxProgressSubscriber<String>(WebViewActivity.this) {
                                @Override
                                protected void onError(ResponseThrowable ex) {
                                LogUtil.d("------请求access_token报错----->"+ex.getErrorMsg());
                                    ToastUtil.show("授权失败！！");
                            }

                                @Override
                                public void onSuccess(String o) {
                                    LogUtil.d("------请求access_token成功----->"+o.toString());
                                    AccessTokenBean tokenBean= JsonUtil.toBean(o,AccessTokenBean.class);
                                    //保存access_token 30天  30天后重新去授权
                                    ACacheUtil.get(WebViewActivity.this).put(AcacheKeys.Token,tokenBean,30*24*60*60);
                                 //   ToastUtil.show("授权成功！！");
                                    finish();

                                }
                            });

                }
                else{ view.loadUrl(url);}

                return true;
            }


                                      @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();// 接受所有网站的证书
           }

        }


        );
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar1!=null){
                    if (newProgress == 100) {
                        progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                    } else {
                        progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                        progressBar1.setProgress(newProgress);//设置进度值
                    }
                }
            }
        });
        if (TextUtils.isEmpty(coinUrl)) return;
        mWebView.loadUrl(coinUrl);
    }

    @Override
    protected BaseView getView() {
        return null;
    }


    @OnClick({R.id.mr_back_layout, R.id.tv_set_Name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mr_back_layout:
                this.finish();
                break;
            case R.id.tv_set_Name:
                break;
        }
    }


}
