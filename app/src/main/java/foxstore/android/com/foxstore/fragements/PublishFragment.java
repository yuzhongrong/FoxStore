package foxstore.android.com.foxstore.fragements;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.StringUtils;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.Utils.recycleview.AnimUtils;
import com.cjwsc.idcm.Utils.recycleview.MyLayoutAnimationHelper;
import com.cjwsc.idcm.adapter.CommonAdapter;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.base.ui.widget.RecycleViewDivider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhl.cbdialog.CBDialogBuilder;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.fragments.BaseFoxStoreFragment;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.common.kes.EventKes;
import foxstore.android.com.common.kes.IntentKeys;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.bean.User;
import foxstore.android.com.foxstore.callback.QueryCallback;
import foxstore.android.com.foxstore.iprovider.DuoDuoAuth2ProviderServices;
import foxstore.android.com.foxstore.bean.Order;
import foxstore.android.com.foxstore.utils.BmobUtils;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static foxstore.android.com.common.kes.EventKes.ORDER_PUBLISH;

/**
 * Created by yuzhongrong on 2018/5/23.
 */

public class PublishFragment extends BaseFoxStoreFragment implements View.OnClickListener {

    private String tag;

    private TextView test;

    private RecyclerView mRecyclerView;
    private CommonAdapter commonAdapter;
    private SmartRefreshLayout mSmartrefresh;
    private int currentpage=1;//默认第一页

    @Autowired
    DuoDuoAuth2ProviderServices mDuoDuoAuth2ProviderServices;

    public static PublishFragment getInstance(String tag) {
        PublishFragment self = new PublishFragment();
        self.tag = tag;
        return self;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_subscribe;
    }

    @Override
    protected void onInitView(Bundle bundle) {

            ARouter.getInstance().inject(this);
          //  test= (TextView) $(R.id.test);
            mRecyclerView= (RecyclerView) $(R.id.subrecycleview);
        mSmartrefresh= (SmartRefreshLayout) $(R.id.mSmartrefresh);
        LinearLayoutManager horizeLinLayout = new LinearLayoutManager(getActivity());
            horizeLinLayout.setOrientation(LinearLayout.VERTICAL);
           mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),0));
            mRecyclerView.setLayoutManager(horizeLinLayout);

            commonAdapter=new CommonAdapter<Order>(R.layout.item_home_layout) {
                @Override
                public void commonconvert(BaseViewHolder helper, Order item) {
                    ((TextView)helper.getView(R.id.keyword)).setText(StringUtils.handlerNull(item.getKeyword()));
                    ((TextView)helper.getView(R.id.dianpu)).setText(StringUtils.handlerNull(item.getStorename()));
                    TextView state= ((TextView)helper.getView(R.id.state));
                    String price="￥"+StringUtils.handlerNull(item.getPrice())+" x"+item.getCount();
                    LogUtil.d("------price----->"+price);
                    ((TextView)helper.getView(R.id.price)).setText(price);
//                    ((TextView)helper.getView(R.id.count)).setText(StringUtils.handlerNull(item.getCount()+"ren"));
                  //  GlideUtil.loadImageView(getActivity(),item.getUrl(),((ImageView)helper.getView(R.id.mainimg)));
                    GlideUtil.loadImageViewWithTransform(getActivity(),item.getUrl(),new RoundedCornersTransformation(10,0),((ImageView)helper.getView(R.id.mainimg)));

                    //设置参团还是开团
                    TextView tv= ((TextView)helper.getView(R.id.type));
                    if(item.isIscan()){//开团
                        tv.setText("开");
                        tv.setBackgroundResource(R.drawable.shape_item_home_blue);
                    }else{//参团
                        tv.setText("参");
                        tv.setBackgroundResource(R.drawable.shape_item_home_red);
                    }
                    //设置状态

                   if( item.getState()==null){
                       state.setVisibility(View.GONE);
                   }else{
                       state .setVisibility(View.VISIBLE);
                   }

                    helper.getConvertView().setOnClickListener(
                            v -> {
//                                Intent intent= new Intent(getActivity(), WebViewActivity.class);
//                                intent.putExtra("url", DuoDuoHttpApi.auth_url1);
//                                intent.putExtra("title", "测试授权");
//
//                                startActivity(intent);

                              //  startActivity(new Intent(getActivity(),InviteActivity.class));

                                //请求前先获取刷单状态判断其是否正在刷单
                                if(item.getState()==null){//null是空闲状态

                                    //查找Person表里面id为6b6c11c537的数据
                                    BmobQuery<User> bmobQuery = new BmobQuery<User>();
                                    ((BaseFoxStoreActivity)getActivity()).showDialog();
                                    bmobQuery.getObject(item.getUserid(), new QueryListener<User>() {
                                        @Override
                                        public void done(User object,BmobException e) {
                                            ((BaseFoxStoreActivity)getActivity()).dismissDialog();
                                            if(e==null){
                                                ARouter.getInstance().build(ActivityKeys.ACTIVITY_INVITE)
                                                        .withString(IntentKeys.DIANPU,item.getStorename())
                                                          .withString(IntentKeys.HEADIMG,object.getHeadimg())
                                                        .navigation(getActivity());
                                            }else{
                                                ToastUtil.show("查询用户信息失败：" );

                                            }
                                        }
                                    });




                                }else if(item.getState().equals("1")){//忙碌状态
                                    popTip();
                                }



                            }
                    );

                }
            };

            mRecyclerView.setAdapter(commonAdapter);



        }

    private void popTip() {

        new CBDialogBuilder(getActivity())
                .setTouchOutSideCancelable(true)
                .showCancelButton(true)
                .setCustomIcon(R.drawable.wushou)
                .setTitleTextColor(R.color.c_2E7FD0)
                .showCancelButton(false)
                .setTitle("刷单中...")
                .setMessage("刷单中，请稍后再尝试！！")
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_TOP)
                .setConfirmButtonText("知道了")
                .setConfirmButtonTextColor(R.color.c_2a82e4)
                .create().show();

    }

    ;



    @Override
    protected void onEvent() {
        //test.setOnClickListener(this);
//
//        /** 模拟数据*/
//        List<SubscribeOrder> datas=new ArrayList<>();
//
//        for(int i=0;i<20;i++){
//            datas.add(new SubscribeOrder());
//        }
//
//        commonAdapter.setNewData(datas);


        mSmartrefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentpage+=1;
                initData(currentpage);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                  currentpage=1;
                initData(currentpage);
            }
        });


    }

    @Override
    protected BaseView getViewImp() {
        return null;
    }

    @Override
    protected void lazyFetchData() {
        initData(1);
    }

    @Override
    public void onClick(View v) {
//        if(v.getId()==R.id.test){
//
//          startActivity(new Intent(getActivity(), WebViewActivity.class));
//

//        }

    }

    private void initData(int page){
        //初始化走一次
        BmobUtils.queryObjectsByPage(getActivity(), Order.class,page,30,new QueryCallback<Order>(){
            @Override
            public void onSuccess(List<Order> datas) {

                if(datas.size()==0){
                    mSmartrefresh.finishLoadmoreWithNoMoreData();
                }else{
                    mSmartrefresh.resetNoMoreData();
                }

                //处理数据加载完成情况
                if(page==1){//加载更多
                    mSmartrefresh.finishRefresh();
                    commonAdapter.setNewData(datas);
                    AnimUtils.playViewAnimation(mRecyclerView, MyLayoutAnimationHelper.getAnimationSetFromRight(), false);

                }else{//刷新数据
                    mSmartrefresh.finishLoadmore();
                    commonAdapter.addData(datas);

            }


            }

            @Override
            public void onFail() {
                ToastUtil.show("拉取数据失败");
                if(mSmartrefresh.isLoading()||mSmartrefresh.isRefreshing()){
                    mSmartrefresh.finishLoadmore();
                    mSmartrefresh.finishRefresh();
                }
            }
        });

    }


    @Subscriber(tag = ORDER_PUBLISH)
    private synchronized void onUpdateNotificationMsg(String gson){
        synchronized (this){

            boolean isadd=true;
            Order order = new Gson().fromJson(gson, new TypeToken<Order>() {
            }.getType());
            //在这之前要判断集中有没有这组数据 有就代表用户已经手动下拉了
            if(commonAdapter!=null){

               List<Order> orders= commonAdapter.getData();
               for(Order item:orders){
                   if(order.getObjectId().equals(item.getObjectId()))isadd=false;
               }

               if(isadd){
                   commonAdapter.addData(0,order);
                //   mRecyclerView.scrollToPosition(0);//这里可能导致用户体验不好用从左边弹出一个VIEW来代替会好些
               }
                EventBus.getDefault().post(order, EventKes.NOTIFY_SHOW);

            }
        }

    }





}
