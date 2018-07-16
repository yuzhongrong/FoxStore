package foxstore.android.com.foxstore.fragements;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.adapter.CommonAdapter;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.base.ui.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import foxstore.android.com.common.fragments.BaseFoxStoreFragment;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.bean.article;
import foxstore.android.com.foxstore.callback.QueryCallback;
import foxstore.android.com.foxstore.utils.BmobUtils;

/**
 * Created by yuzhongrong on 2018/5/23.
 */

public class MyOrderFragment extends BaseFoxStoreFragment {

    private String tag;
    private RecyclerView mRecycleview;
    private CommonAdapter commonAdapter;
    private List<article> datas = new ArrayList<>();


    public static MyOrderFragment getInstance(String tag) {
        MyOrderFragment self = new MyOrderFragment();
        self.tag = tag;
        return self;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mRecycleview = (RecyclerView) $(R.id.recycleview);
        commonAdapter = new CommonAdapter<article>(R.layout.item_subscribe_weixin_layout) {
            @Override
            public void commonconvert(BaseViewHolder helper, article item) {

            }

        };

        mRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewDivider divider = new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,
                25, Color.rgb(244, 244, 244));

        mRecycleview.addItemDecoration(divider);
        mRecycleview.setAdapter(commonAdapter);

    }

    @Override
    protected void onEvent() {

        //模拟数据
//        for(int i=0;i<20;i++){
//
//            commonAdapter.addData(new DemoInfo());
//
//        }


    }

    @Override
    protected BaseView getViewImp() {
        return null;
    }

    @Override
    protected void lazyFetchData() {

//        BmobUtils.<article>queryObjects(getActivity(),article.class,new QueryCallback<article>(){
//
//            @Override
//            public void onFail() {
//
//            }
//
//            @Override
//            public void onSuccess(List<article> datas) {
//
//                commonAdapter.setNewData(datas);
//            }
//        });
//
    }


    }
