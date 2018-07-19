package foxstore.android.com.foxstore.activitys;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.Utils.RxTimerUtil;
import com.cjwsc.idcm.Utils.sound.SoundPlayUtils;
import com.cjwsc.idcm.base.BaseView;

import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.foxstore.R;
import jp.wasabeef.glide.transformations.BlurTransformation;

@Route(path = ActivityKeys.ACTIVITY_INVITE)
public class InviteActivity extends BaseFoxStoreActivity {

    @Autowired(name = "image_url")
    String image_url;
    private ImageView imv;
    private ImageButton cell_close;
    private TextView cell_txt;
    private TextView state_text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        ARouter.getInstance().inject(this);
        imv=(ImageView) $(R.id.bg_blur);
        cell_close= (ImageButton) $(R.id.cell_close);
        cell_txt=(TextView) $(R.id.cell_txt);
        state_text=(TextView) $(R.id.state_text);

    }

    @Override
    protected void onEvent() {
        GlideUtil.loadImageViewWithTransform(this,R.mipmap.bg_blur,new BlurTransformation(10,8),imv);

        cell_close.setOnClickListener(v->{
            SoundPlayUtils.play(2); //
           this.finish();

        });
        state_text.setVisibility(View.VISIBLE);

        RxTimerUtil.startTime(this,state_text,"正在邀请对方刷单",30,number -> {
            //倒计时完成关闭页面
            this.finish();

        });


    }

    @Override
    protected BaseView getView() {
        return null;
    }
}
