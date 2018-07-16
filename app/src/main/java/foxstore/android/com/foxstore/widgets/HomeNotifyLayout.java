package foxstore.android.com.foxstore.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.cjwsc.idcm.Utils.RxTimerUtil;
import com.cjwsc.idcm.Utils.recycleview.AnimUtils;
import com.cjwsc.idcm.Utils.recycleview.MyLayoutAnimationHelper;
import com.cjwsc.idcm.Utils.sound.SoundPlayUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import foxstore.android.com.common.kes.EventKes;
import foxstore.android.com.foxstore.model.bean.Order;
import foxstore.android.com.foxstore.tencent_xg.common.NotificationService;

/**垃圾回收模式处理自己的事情（弹出，消失，从数据库删除等操作）*/

public class HomeNotifyLayout extends RelativeLayout {
    public HomeNotifyLayout(Context context) {
        super(context);
    }

    public HomeNotifyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
    }

   @Subscriber(tag = EventKes.NOTIFY_SHOW)
    private void show(Order bean){
        this.setVisibility(VISIBLE);
        AnimUtils.playViewAnimation(this, MyLayoutAnimationHelper.getAnimationSetFromLeft(),false);
       SoundPlayUtils.play(1); //推送声音
       RxTimerUtil.timer(4, new RxTimerUtil.IRxNext() {
           @Override
           public void doNext(long number) {
               hide();
               NotificationService.getInstance(getContext()).delete(bean.getObjectId());
           }
       });
    }

    private void hide(){
        this.setVisibility(GONE);
    }

}
