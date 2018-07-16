package foxstore.android.com.foxstore.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.recycleview.AnimUtils;
import com.cjwsc.idcm.Utils.recycleview.MyLayoutAnimationHelper;
import com.cjwsc.idcm.base.BaseView;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import foxstore.android.com.common.fragments.BaseFoxStoreFragment;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.activitys.StartPublishActivity;

/**
 * Created by yuzhongrong on 2018/5/23.
 */

public class HomeFragment extends BaseFoxStoreFragment implements View.OnClickListener {

    private ImageView tip;
    private ViewPager mFragmentviewpager;
    private SlidingTabLayout mTablayout;
    private ArrayList<Fragment> mFragments=new ArrayList<>();
    String[] mTitles;
    private TextView publish;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onInitView(Bundle bundle) {

        mTablayout=rootView.findViewById(R.id.tablayout);
        mFragmentviewpager=rootView.findViewById(R.id.fragmentviewpager);
        tip= (ImageView) $(R.id.tip);
        publish= (TextView) $(R.id.publish);
        mTitles = new String[]{getString(R.string.str_subscribe),getString(R.string.same), getString(R.string.my)};
        mFragments.add(PublishFragment.getInstance("SUBSCRIBE"));
        mFragments.add(SamePriceFragment.getInstance("HOT"));
        mFragments.add(MyOrderFragment.getInstance("MYORDER"));
    }


    private void initViewPager() {
        mTablayout.setViewPager(mFragmentviewpager,mTitles,getActivity(),mFragments);
    }

    @Override
    protected void onEvent() {

//        mFragmentviewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if(position==1){
//                    tip.setBackground(getResources().getDrawable(R.drawable.down_sel));
//                }else{
//                    tip.setBackground(getResources().getDrawable(R.drawable.down));
//
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        publish.setOnClickListener(this);





    }

    @Override
    protected BaseView getViewImp() {
        return null;
    }

    @Override
    protected void lazyFetchData() {
        initViewPager();
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.publish){//发布刷单

            startActivity(new Intent(getActivity(), StartPublishActivity.class));

        }
    }
}
