package foxstore.android.com.foxstore.popwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.Utils.JsonUtil;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.StringUtils;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.adapter.CommonAdapter;
import com.cjwsc.idcm.base.ui.widget.RecycleViewDivider;
import com.cjwsc.idcm.net.callback.RxProgressSubscriber;
import com.cjwsc.idcm.net.exception.ResponseThrowable;

import java.util.List;
import java.util.Map;

import foxstore.android.com.common.widgets.DuoDuoSignTools;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.activitys.StartPublishActivity;
import foxstore.android.com.foxstore.bean.GoodsBean;
import foxstore.android.com.foxstore.bean.GoodsDetailBean;
import foxstore.android.com.foxstore.iprovider.DuoDuoGoodsProviderServices;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import razerdp.basepopup.BasePopupWindow;
import foxstore.android.com.foxstore.bean.GoodsBean.GoodsListGetResponseBean.GoodsListBean;

public class GoodsListPopWindow extends BasePopupWindow {
    private RecyclerView recyclerView;
    private CommonAdapter commonAdapter;
    private List<GoodsListBean> datas;
    @Autowired
    DuoDuoGoodsProviderServices duoDuoGoodsProviderServices;
    public GoodsListPopWindow(Context context) {
        super(context);

    }



    @Override
    protected Animation initShowAnimation() {
        return AnimationUtils.loadAnimation(getContext(), com.cjwsc.idcm.R.anim.slide_in_bottom);
    }

    @Override
    protected Animation initExitAnimation() {
        return AnimationUtils.loadAnimation(getContext(), com.cjwsc.idcm.R.anim.slide_out_bottom);
    }
    @Override
    public View getClickToDismissView() {
        return getPopupWindowView().findViewById(R.id.cancel);
    }

    @Override
    public View onCreatePopupView() {
        ARouter.getInstance().inject(this);
     View  root=  LayoutInflater.from(getContext()).inflate(R.layout.pop_goods_list_layout,null);
        recyclerView=root.findViewById(R.id.goods_list);
        initView();
        return root;
    }

    @Override
    public View initAnimaView() {
        return getPopupWindowView().findViewById(R.id.root);
    }

    private void initView(){

        LinearLayoutManager horizeLinLayout = new LinearLayoutManager(getContext());
        horizeLinLayout.setOrientation(LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(),0));
        recyclerView.setLayoutManager(horizeLinLayout);

        commonAdapter=new CommonAdapter<GoodsListBean>(R.layout.item_goods_list) {
            @Override
            public void commonconvert(BaseViewHolder helper, GoodsListBean item) {
                GlideUtil.loadImageViewWithTransform(getContext(),item.getImage_url(),new RoundedCornersTransformation(10,0),((ImageView)helper.getView(R.id.icon)));
                ((TextView)helper.getView(R.id.goods_quantity)).setText("库存:"+StringUtils.handlerNull(item.getGoods_quantity()+""));
                ((TextView)helper.getView(R.id.goods_name)).setText(StringUtils.handlerNull(item.getGoods_name()));
                helper.getConvertView().setOnClickListener(v -> {

                    Map<String,String> maps=   DuoDuoSignTools.getDefaultParams();
                    maps.put("type","pdd.goods.detail.get");
                    maps.put("goods_id",item.getGoods_id()+"");
                    DuoDuoSignTools.sign(maps);
                    duoDuoGoodsProviderServices.getCommonData(maps).subscribe(new RxProgressSubscriber<String>((StartPublishActivity)getContext()) {
                        @Override
                        public void onSuccess(String data) {
                            LogUtil.d("--------获取商品成功------->"+data);
                            ToastUtil.show("获取商品详情成功！");
                            GoodsDetailBean goodsDetailBean= JsonUtil.toBean(data,GoodsDetailBean.class);
                            detailCallBack.onResult(goodsDetailBean);
                            dismiss();

                        }

                        @Override
                        protected void onError(ResponseThrowable ex) {
                            LogUtil.d("--------获取商品详情失败------->"+ex.getErrorMsg());
                            ToastUtil.show("获取商品详情失败！");
                            dismiss();
                        }


                    });

                });

            }


        };
        recyclerView.setAdapter(commonAdapter);



//        for(int i=0;i<15;i++){
//
//            commonAdapter.addData(new GoodsListBean());
//        }

    }

    public GoodsListPopWindow setDatas(List<GoodsListBean> datas, DetailCallBack detailCallBack){
        commonAdapter.setNewData(datas);
        this.detailCallBack=detailCallBack;
        return this;
    }

    private DetailCallBack detailCallBack;

    public interface DetailCallBack{
        void onResult(GoodsDetailBean goodsDetailBean);
    }

}
