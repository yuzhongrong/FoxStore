package foxstore.android.com.foxstore.activitys;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.base.BaseView;

import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.foxstore.R;
import jp.wasabeef.glide.transformations.BlurTransformation;

@Route(path = ActivityKeys.ACTIVITY_INVITE)
public class InviteReceiverActivity extends BaseFoxStoreActivity {

    @Autowired(name = "image_url")
    String image_url;
    private ImageView imv;
    private ImageButton cell;
    private TextView cell_txt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        ARouter.getInstance().inject(this);
        imv=(ImageView) $(R.id.bg_blur);
        cell= (ImageButton) $(R.id.cell);
        cell_txt=(TextView) $(R.id.cell_txt);

    }

    @Override
    protected void onEvent() {
        GlideUtil.loadImageViewWithTransform(this,R.mipmap.bg_blur,new BlurTransformation(10,8),imv);

        cell.setOnClickListener(v->{
            cell.setImageDrawable(getResources().getDrawable(R.drawable.seletor_shap_cell_close));
            cell_txt.setText("取消");

        });
    }

    @Override
    protected BaseView getView() {
        return null;
    }
}
