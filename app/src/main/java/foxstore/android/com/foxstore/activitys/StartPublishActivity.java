package foxstore.android.com.foxstore.activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.Utils.GsonUtil;
import com.cjwsc.idcm.Utils.JsonUtil;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.StringUtils;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.net.callback.RxProgressSubscriber;
import com.cjwsc.idcm.net.callback.RxSubscriber;
import com.cjwsc.idcm.net.exception.ResponseThrowable;
import com.flyco.tablayout.SegmentTabLayout;
import com.zhl.cbdialog.CBDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.common.kes.AcacheKeys;
import foxstore.android.com.common.kes.ActivityKeys;
import foxstore.android.com.common.widgets.DuoDuoSignTools;
import foxstore.android.com.foxstore.R;
import foxstore.android.com.foxstore.api.DuoDuoHttpApi;
import foxstore.android.com.common.bean.AccessTokenBean;
import foxstore.android.com.foxstore.bean.GoodsBean;
import foxstore.android.com.foxstore.bean.GoodsDetailBean;
import foxstore.android.com.foxstore.callback.QueryCallback;
import foxstore.android.com.foxstore.iprovider.DuoDuoGoodsProviderServices;
import foxstore.android.com.foxstore.model.bean.Order;
import foxstore.android.com.foxstore.popwindow.GoodsListPopWindow;
import foxstore.android.com.foxstore.tencent_xg.bean.ClickAction;
import foxstore.android.com.foxstore.tencent_xg.bean.Message;
import foxstore.android.com.foxstore.tencent_xg.bean.Style;
import foxstore.android.com.foxstore.tencent_xg.bean.TimeInterval;
import foxstore.android.com.foxstore.tencent_xg.callback.CommonCallBack;
import foxstore.android.com.foxstore.tencent_xg.common.XingeApp;
import foxstore.android.com.foxstore.utils.BmobUtils;
import foxstore.android.com.foxstore.widgets.CusEditText;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.zhl.cbdialog.CBDialogBuilder.onDialogbtnClickListener.BUTTON_CANCEL;
import static com.zhl.cbdialog.CBDialogBuilder.onDialogbtnClickListener.BUTTON_CONFIRM;
import static foxstore.android.com.common.utils.MetaDataUtils.getMetaData;

/**
 * Created by yuzhongrong on 2018/6/25.
 */

public class StartPublishActivity extends BaseFoxStoreActivity implements View.OnClickListener{

    private ImageView back;
    private SegmentTabLayout tabLayout1;
    private SegmentTabLayout tabLayout2;
    private ImageView mainimg;
    private TextView start;
    private String[] mTitles = {"开团", "参团"};
    private String[] mTitles1 = {"叠加x1", "x2","x3","x4"};
   // private String url="";//上传图片成功后的url
    private GoodsDetailBean detailBean;

    private CusEditText edit1;
    //private CusEditText edit2;
   // private CusEditText edit3;
    private TextView simpleprice;
    private TextView goods_name;

     @Autowired
    DuoDuoGoodsProviderServices duoDuoGoodsProviderServices;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_publish_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {

        ARouter.getInstance().inject(this);
        back= (ImageView) $(R.id.back);
        tabLayout1= (SegmentTabLayout   ) $(R.id.tl_1);
        tabLayout2=(SegmentTabLayout   ) $(R.id.count);
        start= (TextView) $(R.id.start);
        mainimg=(ImageView)$(R.id.mainimg);
        edit1=(CusEditText)$(R.id.edit1);
      //  edit2=(CusEditText)$(R.id.edit2);
      //  edit3=(CusEditText)$(R.id.edit3);
        simpleprice=(TextView)$(R.id.simpleprice) ;
        goods_name=(TextView)$(R.id.goods_name) ;
        tabLayout1.setTabData(mTitles);
        tabLayout2.setTabData(mTitles1);





    }

    @Override
    protected void onEvent() {

        start.setOnClickListener(this);
        back.setOnClickListener(this);
        mainimg.setOnClickListener(this);
    }

    @Override
    protected BaseView getView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){
            this.finish();
        }else if(v.getId()==R.id.start){

            if(detailBean==null){
                ToastUtil.show("请选择商品");
                return;
            }

            if(TextUtils.isEmpty(edit1.getText())){
                ToastUtil.show("请填入正确的关键词");
                return;

            }
//            if(TextUtils.isEmpty(edit2.getText())){
//                ToastUtil.show("请填入正确的店铺名");
//                return;
//
//            }
//
//            if(TextUtils.isEmpty(edit3.getText())){
//                ToastUtil.show("请填入正确价格");
//                return;
//
//            }

          startPrePublish();//预发布

        }else if(v.getId()==R.id.mainimg){
//            TakePhotoUtils.showDialog(this, new TakePhotoUtils.CallBack() {
//                @Override
//                public void onActivityResult(String path) {
//
//                    if(!TextUtils.isEmpty(path)){
//
//                        GlideUtil.loadImageView(StartPublishActivity.this,path,mainimg);
//                        BmobUtils.getInstance(StartPublishActivity.this).uploadImg(new String[]{path}, new BmobUtils.UploadCallBack() {
//                            @Override
//                            public void onUploadSuccess(String urls) {
//
//                                url=urls;
//                                ToastUtil.show("上传成功");
//
//                            }
//
//                            @Override
//                            public void onUploadError(String errormsg) {
//                                ToastUtil.show("上传失败");
//
//                            }
//                        });
//
//                    }
//                }
//            });

            getGoodsDatas();

        }
    }

    private void pushAllDevice(String s, CommonCallBack callBack) {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> observable) throws Exception {
                sendPushAllDevice(s,s,observable);//被观察这放到子线程去
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        callBack.onSuccess(s);
                    }

                    @Override
                    protected void onError(ResponseThrowable ex) {
                        callBack.onError(ex.getErrorMsg());
                    }
                });



    }

    private void sendPushAllDevice(String title,String content,FlowableEmitter<String> observable){
        String secretKey = null;
        int ret_code=0;
        try {
            secretKey = (String) getMetaData(getApplicationContext(), "XG_V2_SECRET_KEY", "863fa8b9f23110820b07039f8a085471");
            long accID = (Integer) getMetaData(getApplicationContext(), "XG_V2_ACCESS_ID", "2100298336");
            XingeApp xinge = new XingeApp(accID, secretKey);
            JSONObject ret = xinge.pushAllDevice(0,makeMessage(title,content));
            ret_code = ret.getInt("ret_code");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(ret_code==0){ //请求信鸽服务器发送推送成功
            observable.onNext(ret_code+"");
        }else{//请求信鸽服务器发送推送失败
            observable.onError(new ResponseThrowable(ret_code+""));
        }


    }


    private Message makeMessage(String title,String content){
      Message message = new Message();
        message.setType(Message.TYPE_MESSAGE);
        Style style = new Style(1);
        style = new Style(3, 1, 0, 1, 0);
        ClickAction action = new ClickAction();
        action.setActionType(ClickAction.TYPE_URL);
        action.setUrl("http://xg.qq.com");
        Map<String, Object> custom = new HashMap<String, Object>();
        custom.put("key", title);
        custom.put("key1", content);
        message.setTitle(title);
        message.setContent(content);
        message.setStyle(style);
        message.setAction(action);
        message.setCustom(custom);
        TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
        message.addAcceptTime(acceptTime1);
        return message;

    }


    private int getDjCount(SegmentTabLayout tabLayout) {

        return tabLayout.getCurrentTab()+1;
    }




    private void startPrePublish(){

        //发布之前先要查询发布单的唯一性
        BmobUtils.queryObjects(this,Order.class,"goods_id",detailBean.getGoods_detail_get_response().getGoods_id(),new QueryCallback<Order>(){

            @Override
            public void onSuccess(List<Order> datas) {

                if(datas.size()>=1){
                    LogUtil.d("-----商品已发布---->");
                    ToastUtil.show("商品已发布，请勿重复发布！");
                }else{
                    LogUtil.d("-----商未发布---->");
                    startPublish();
                }
            }

            @Override
            public void onFail() {
                ToastUtil.show("服务器开小差了哦 ，亲！");
            }
        });

    }

    //发布订单流程
    private void startPublish(){

       String price= simpleprice.getText().toString();
        Order order=new Order(BmobUser.getCurrentUser().getObjectId(),detailBean.getGoods_detail_get_response().getGoods_id(),detailBean.getGoods_detail_get_response().getHd_thumb_url(),tabLayout1.getCurrentTab()==0?true:false,getDjCount(tabLayout2),goods_name.getText().toString()
                ,"懒小姐服饰",price.substring(price.indexOf("￥")+1));

        BmobUtils.insertObject(this, order, new BmobUtils.CallBack() {
            @Override
            public void onSuccess(String s) {
                ToastUtil.show("恭喜您,发布成功");
                //把刚才保存的对象转换成json 推送到所有手机(为了节约流量不请求网络)
                order.setObjectId(s);
                String  gson =GsonUtil.object2Json(order);
                pushAllDevice(gson, new CommonCallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.d("发送推送成功");
                        finish();
                    }

                    @Override
                    public void onError(String errormsg) {
                        LogUtil.d("发送推送失败");
                    }
                });
            }

            @Override
            public void onError(String errormsg) {
                ToastUtil.show("发布失败,请重新发布");
            }
        });


    }

    private void getGoodsDatas(){
        AccessTokenBean tokenBean= (AccessTokenBean) ACacheUtil.get(StartPublishActivity.this).getAsObject(AcacheKeys.Token);
        if(tokenBean!=null){
            //获取店铺商品列表
            // ToastUtil.show("获取拼多多商品列表");
            Map<String,String> maps=   DuoDuoSignTools.getDefaultParams();
            maps.put("type","pdd.goods.list.get");
            maps.put("is_onsale","1");
            DuoDuoSignTools.sign(maps);
            duoDuoGoodsProviderServices.getGoodsLists(maps).subscribe(new RxProgressSubscriber<String>(this) {
                @Override
                public void onSuccess(String data) {
                    LogUtil.d("--------获取商品列表成功------->"+data);
                    GoodsBean goodsBean= JsonUtil.toBean(data,GoodsBean.class);
                    List<GoodsBean.GoodsListGetResponseBean.GoodsListBean> listBeans= goodsBean.getGoods_list_get_response().getGoods_list();
                    new GoodsListPopWindow(StartPublishActivity.this).setDatas(listBeans,goodsDetailBean->{
                        //获取到商品详情
                        if(goodsDetailBean!=null){
                            detailBean=goodsDetailBean;
                            GlideUtil.loadImageViewWithTransform(StartPublishActivity.this,goodsDetailBean
                                            .getGoods_detail_get_response()
                                            .getHd_thumb_url(),
                                    new RoundedCornersTransformation(10,0)
                                    ,mainimg);

                            goods_name.setText(StringUtils.handlerNull(goodsDetailBean.getGoods_detail_get_response().getGoods_name()));
                            int multyprice=goodsDetailBean.getGoods_detail_get_response().getSku_list().get(0).getMulti_price();
                            simpleprice.setText(StringUtils.handlerNull(getMoneyType(multyprice/100+"."+multyprice%100)));

                        }

                    }).showPopupWindow();
                }

                @Override
                protected void onError(ResponseThrowable ex) {
                    LogUtil.d("--------获取商品列表失败------->"+ex.getErrorMsg());

                }
            });


        }else{//授权流程
            new CBDialogBuilder(this)
                    .setTouchOutSideCancelable(true)
                    .showCancelButton(true)
                    .setTitle("授权")
                    .setMessage("首次发布刷单需要拼多多授权")
                    .setConfirmButtonText("确定")
                    .setCancelButtonText("取消")
                    .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_TOP)
                    .setButtonClickListener(true, ( context,  dialog,  i)->{
                        switch (i){
                            case BUTTON_CONFIRM ://确认
                              //  ARouter.getInstance().build(ActivityKeys.ACTIVITY_AUTHWEB).withString("url", DuoDuoHttpApi.auth_login).navigation();
                                Intent intent=new Intent(this,WebViewActivity.class);
                                intent.putExtra("url", DuoDuoHttpApi.auth_login);
                                intent.putExtra("title", "授权");
                                startActivityForResult(intent,0x1);

                                break;
                            case BUTTON_CANCEL ://取消
                                break;
                        }

                    })
                    .create().show();

        }
    }


    public static String getMoneyType(String string) {
        // 把string类型的货币转换为double类型。
        Double numDouble = Double.parseDouble(string);
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 把转换后的货币String类型返回
        String numString = format.format(numDouble);
        return numString;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(requestCode==0x1){//授权成功回来
             getGoodsDatas();

         }
    }
}
