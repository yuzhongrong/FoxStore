package foxstore.android.com.foxstore.activitys;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.LoginUtils;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.AppManager;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.constant.BaseSignalConstants;
import com.cjwsc.idcm.widget.navigatetab.NavigateTabBar;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.fragements.HomeFragment;
import foxstore.android.com.foxstore.fragements.MessageFragment;
import foxstore.android.com.foxstore.fragements.MineFragment;


/**
 * Created by admin-2 on 2018/3/13.
 */
@Route(path = ActivityKeys.ACTIVITY_MAIN)
public class MainActivity extends BaseFoxStoreActivity {

    private NavigateTabBar mNavigateTabBar;
    private String mLanguagePosition;
    private Resources mResources;
    private Configuration mConfig;
    private DisplayMetrics mDm;
    private static String mLanguageCode;
    private static String mServerCode;
    public static boolean isOpen = false;
    private String languageTag = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView(Bundle bundle) {

        isOpen = true;
        initNavigeteTabBar();

        Intent intent = getIntent();
        Bundle bundleString = intent.getBundleExtra("Language");
        if (bundleString != null) {
            languageTag = bundleString.getString("language");
        }
    }




    @Override
    protected void onEvent() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        //既然第一次进来和从后台进来 都要更新何不放在这里更新呢
    }

    @Override
    protected BaseView getView() {
        return null;
    }

    private void initNavigeteTabBar() {
        mNavigateTabBar = (NavigateTabBar) findViewById(R.id.home_navigate);

        String HOME_PAGE = getString(R.string.home);
        mNavigateTabBar.addTab(HomeFragment.class,
                new NavigateTabBar.TabParam(
                        R.drawable.home,
                        R.drawable.home_sel,
                        HOME_PAGE));
        String MESSAGE_PAGE = getString(R.string.message);
        mNavigateTabBar.addTab(MessageFragment.class,
                new NavigateTabBar.TabParam(
                        R.drawable.news,
                        R.drawable.news_sel,
                        MESSAGE_PAGE));
        String MINE_PAGE = getString(R.string.str_mine);
        mNavigateTabBar.addTab(MineFragment.class,
                new NavigateTabBar.TabParam(
                        R.drawable.my,
                        R.drawable.my_sel,
                        MINE_PAGE));

        mNavigateTabBar.setTabSelectListener(new NavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(NavigateTabBar.ViewHolder holder) {
                if (HOME_PAGE.equals(holder.tag.toString())) {
                    mNavigateTabBar.showFragment(holder);
                } else if (MESSAGE_PAGE.equals(holder.tag.toString())) {
                    mNavigateTabBar.showFragment(holder);
                } else if (MINE_PAGE.equals(holder.tag.toString())) {
                    //EventBus.getDefault().postSticky(new TradeSeletedEvent());
                    mNavigateTabBar.showFragment(holder);

                }
            }
        });
    }

    /**
     * 点击手机返回键finish app
     */
    @Override
    public void onBackPressed() {
        exitApp();
    }

    /**
     * 退出应用
     */

    private long exitTime = 0;

    public void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.show(getString(R.string.tv_again_click_out));
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getInstance().finishAllActivity();
            finish();
            System.exit(0);
        }
    }

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }



    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }
}
