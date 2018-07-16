package foxstore.android.com.foxstore.popwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import foxstore.android.com.foxstore.R;
import razerdp.basepopup.BasePopupWindow;

public  class AuthPopWindow extends BasePopupWindow {
    private View root;
    public AuthPopWindow(Context context) {
        super(context);
    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    protected Animation initExitAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {

        View view=createPopupById(R.layout.pop_auth_layout);
        root=view.findViewById(R.id.root);
        return view;
    }

    @Override
    public View initAnimaView() {
        return null;
    }


}
