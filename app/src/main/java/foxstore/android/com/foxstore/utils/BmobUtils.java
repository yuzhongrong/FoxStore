package foxstore.android.com.foxstore.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.cjwsc.idcm.Utils.RetrofitUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.api.FoxManHttpApi;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.cjwsc.idcm.net.callback.RxSubscriber;
import com.cjwsc.idcm.net.exception.ResponseThrowable;
import com.cjwsc.idcm.net.http.HttpUtils;
import com.cjwsc.idcm.net.response.HttpResponse;
import com.cjwsc.idcm.net.transformer.DefaultTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import foxstore.android.com.common.activitys.BaseFoxStoreActivity;
import foxstore.android.com.foxstore.callback.QueryCallback;
import foxstore.android.com.foxstore.model.bean.MainImg;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rx.Subscriber;
import top.zibin.luban.Luban;

/**
 * Created by yuzhongrong on 2018/5/26.
 */

public class BmobUtils {


    private static BmobUtils instance;
    private static Context mContext;

    public static BmobUtils getInstance(Context context){

            if(instance==null){

                instance=new BmobUtils();
            }

        mContext=context;

        return instance;

    }


    //查找一个表中的多条数据
    public static <T> void queryObjects(Context context,Class<T> classobject,String where_key,String where_value, QueryCallback<T> callback){
        final BmobQuery<T> bmobQuery	 = new BmobQuery();
        bmobQuery.order("createdAt");
        bmobQuery.addWhereEqualTo(where_key,where_value);
        //先判断是否有缓存
     //   boolean isCache = bmobQuery.hasCachedResult(article.class);
//        if(isCache){
//            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
//        }else{
//            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//        }
//		observable形式
        ((BaseFoxStoreActivity)context).addSubscribe(    bmobQuery.findObjectsObservable(classobject)
                .subscribe(new Subscriber<List<T>>() {

                    @Override
                    public void onStart() {
                        ((BaseActivity)context).showDialog();
                    }

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity)context).dismissDialog();
                        Log.d("findObjectsObservable",e.getMessage());
                        callback.onFail();

                    }

                    @Override
                    public void onNext(List<T> datas) {

                        ((BaseActivity)context).dismissDialog();
                        callback.onSuccess(datas);
                        //      com.orhanobut.logger.Logger.d("查询成功：共"+persons.size()+"条数据。");
                        //   com.orhanobut.logger.Logger.d("查询成功：共"+persons.size()+"条数据。");
                    }
                }));



    }



    /**分页查找*/
    //查找一个表中的多条数据 从第一页开始1......n
    public static <T> void queryObjectsByPage(Context context,Class<T> classobject,int page,int pagecount, QueryCallback<T> callback){
        final BmobQuery<T> bmobQuery	 = new BmobQuery();

        //先判断是否有缓存
//           boolean isCache = bmobQuery.hasCachedResult(classobject);
//        if(isCache){
//            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
//        }else{
//            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//        }
//		observable形式
        bmobQuery.order("-createdAt");
        bmobQuery.setLimit(pagecount);
        bmobQuery.setSkip((page-1)*pagecount);
        ((BaseFoxStoreActivity)context).addSubscribe( bmobQuery.findObjectsObservable(classobject)
                .subscribe(new Subscriber<List<T>>() {
                    @Override
                    public void onStart() {
                        if(page==1)((BaseActivity)context).showDialog();
                    }

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity)context).dismissDialog();
                        Log.d("findObjectsObservable",e.getMessage());
                        callback.onFail();

                    }

                    @Override
                    public void onNext(List<T> datas) {

                        ((BaseActivity)context).dismissDialog();
                        callback.onSuccess(datas);
                        //      com.orhanobut.logger.Logger.d("查询成功：共"+persons.size()+"条数据。");
                        //   com.orhanobut.logger. Logger.d("查询成功：共"+persons.size()+"条数据。");
                    }
                }));

    }




    /**内部查找*/
    //查找一个表中的多条数据 从第一页开始1......n
    public static <T> void queryObjectsByBackground(Context context,Class<T> classobject, QueryCallback<T> callback){
        final BmobQuery<T> bmobQuery	 = new BmobQuery();

        //先判断是否有缓存
//           boolean isCache = bmobQuery.hasCachedResult(classobject);
//        if(isCache){
//            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
//        }else{
//            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//        }
//		observable形式
        bmobQuery.order("-createdAt");
        ((BaseFoxStoreActivity)context).addSubscribe( bmobQuery.findObjectsObservable(classobject)
                .subscribe(new Subscriber<List<T>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.d("findObjectsObservable",e.getMessage());
                        callback.onFail();

                    }

                    @Override
                    public void onNext(List<T> datas) {

                        callback.onSuccess(datas);
                    }
                }));

    }



    /**上传图片*/

    @SuppressLint("CheckResult")
    public  void uploadImg(String[] path, CallBack callBack){

           Flowable.just(Arrays.asList(path))
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(List<String> strings) throws Exception {
                        return Luban.with(BaseApplication.getContext()).ignoreBy(400).load(strings).get();
                    }
                }).subscribe(new RxSubscriber<List<File>>() {
               @Override
               public void onSuccess(List<File> files) {
                   ((BaseFoxStoreActivity)mContext).showDialog();
                   String[] paths=new String[]{files.get(0).getAbsolutePath()};
                   BmobFile.uploadBatch(paths, new UploadBatchListener() {


                       @Override
                       public void onSuccess(List<BmobFile> files, List<String> urls) {
                           Log.d("-uploadImg-onSuccess-->",urls.toString());

                           // log("insertDataWithMany -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
//                if(urls.size()==2){//如果全部上传完，则更新该条记录
//                  //  Song song =new Song("汪峰0","北京北京0",files.get(0),files.get(1));
//                  //  insertObject(song);
//                }else{
//                    //有可能上传不完整，中间可能会存在未上传成功的情况，你可以自行处理
//                }

                           insertObject(mContext,new MainImg(urls.get(0)),callBack);



                       }
                       @Override
                       public void onError(int statuscode, String errormsg) {
                           //  showToast("错误码"+statuscode +",错误描述："+errormsg);
                           Log.d("-uploadImg---onError-->",errormsg);
                           ToastUtil.show("上传失败");
                           // callBack.onError(errormsg);
                           ((BaseFoxStoreActivity)mContext).dismissDialog();
                       }
                       @Override
                       public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                           // log("insertDataWithMany -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
                       }
                   });
               }

               @Override
               protected void onError(ResponseThrowable ex) {
                  ToastUtil.show("上传图片失败");

               }
           });









    }



    /**创建一个数据*/
    /** 创建操作
     * insertObject
     * @return void
     * @throws
     */
    public   static void insertObject(Context context,final BmobObject obj,CallBack callBack){

      final UploadCallBack muploadCallBack = (callBack instanceof UploadCallBack)?(UploadCallBack)callBack:null;
        if(muploadCallBack==null){
            ((BaseFoxStoreActivity)context).showDialog();//单独使用这个方法保存数据的时候要显示对话框，并不影响嵌套使用

        }
        //如果是登录或者注册
        if(obj instanceof BmobUser){
            ((BmobUser)obj).signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser user, BmobException e) {
                    ((BaseFoxStoreActivity)context).dismissDialog();
                    if(e ==null){

                        callBack.onSuccess(user.toString());
                    }else{
                        callBack.onError(e.getErrorCode()+"");
                    }

                }
            });

        }else{
            obj.save(new SaveListener<String>() {

                @Override
                public void done(String s, BmobException e) {

                    if(e==null){
                        //  ToastUtil.show("-->创建数据成功：" + s);
                        if(muploadCallBack !=null){
                            muploadCallBack.onUploadSuccess(((MainImg)obj).getUrl());
                        }else{
                            callBack.onSuccess(s);

                        }

                    }else{
                        if(muploadCallBack !=null){
                            muploadCallBack.onUploadError(e.getMessage());
                        }else{
                            callBack.onError(e.getMessage());
                        }


                        //  ToastUtil.show("-->创建数据失败：" + e.getErrorCode()+",msg = "+e.getMessage());
                    }

                    ((BaseFoxStoreActivity)context).dismissDialog();
                }
            });
        }

    }





    public  interface CallBack{

        void  onSuccess(String s);

        void onError(String errormsg);

    }

    public static class UploadCallBack implements CallBack{

         public   void  onUploadSuccess(String s){};

        public void onUploadError(String errormsg){};


        @Override
        public void onSuccess(String s) {

        }

        @Override
        public void onError(String errormsg) {

        }
    }

}
