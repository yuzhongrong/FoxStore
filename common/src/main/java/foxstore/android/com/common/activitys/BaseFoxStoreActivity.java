package foxstore.android.com.common.activitys;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.AppManager;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseModel;
import com.cjwsc.idcm.base.BasePresenter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import foxstore.android.com.common.R;
import foxstore.android.com.common.utils.StatusBarUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by yuzhongrong on 2018/5/23.
 *
 */

public abstract class BaseFoxStoreActivity<M extends BaseModel, P extends BasePresenter>
        extends BaseActivity<M,P> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.with(this).setDrawable(getResources().getDrawable(R.drawable.icon_statusbar)).init();
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//背景为白色的时候，顶部状态栏的字体显示为黑体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }




    }


    //提供给接口暴露服务使用 1.0不支持foxman2.0 的rx 所以才这么写
    private CompositeSubscription subscription = new   CompositeSubscription();
    public void addSubscribe(Subscription sub) {
        if (sub != null && !sub.isUnsubscribed()) {
            subscription.add(sub);
        }
    }

    public void unSubscribe() {
        if(subscription.hasSubscriptions()){
            subscription.unsubscribe();
        }
    }


    @Override
    protected void onDestroy() {
        unSubscribe();
        super.onDestroy();
    }


    /**
     * 退出应用
     */

    private long exitTime = 0;

    protected void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.show(getString(R.string.tv_again_click_out));
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getInstance().finishAllActivity();
            finish();
            System.exit(0);
        }
    }

}
