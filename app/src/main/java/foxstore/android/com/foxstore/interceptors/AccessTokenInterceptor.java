package foxstore.android.com.foxstore.interceptors;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.JsonUtil;
import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.cjwsc.idcm.net.exception.ServerException;

import org.json.JSONObject;

import java.io.IOException;

import foxstore.android.com.common.bean.AccessTokenBean;
import foxstore.android.com.common.kes.AcacheKeys;
import foxstore.android.com.foxstore.activitys.StartPublishActivity;
import foxstore.android.com.foxstore.bean.ResponseError;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AccessTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        //String url = oldRequest.url().toString();
        Response response = null;

        // 新的请求,添加参数
      //  Request newRequest = addParam(oldRequest);
      //  response = chain.proceed(newRequest);

        response = chain.proceed(oldRequest);
        ResponseBody value = response.body();
        byte[] resp = value.bytes();
       // String json = new String(resp, "UTF-8");
        if(value.toString().equals("error_response")){//请求结果报错

            //转换成bean
            ResponseError responsebean= JsonUtil.toBean(value.toString(),ResponseError.class);
             if(responsebean.getError_response()!=null){
                   if(responsebean.getError_response().getError_code()==100119){//重刷授权
                       AccessTokenBean  accessTokenBean= JsonUtil.toBean(response.body().string(), AccessTokenBean.class);

                       // token失效，重新执行请求
                       Request newTokenRequest = addParam(oldRequest,accessTokenBean.getRefresh_token());//刷新token请求
                       response = chain.proceed(newTokenRequest);
                       // 保存刷新后的access_token
                       ACacheUtil.get(BaseApplication.getContext()).put(AcacheKeys.Token,accessTokenBean);
                       //再去请求原request
                       response = chain.proceed(oldRequest);


                   }else if(responsebean.getError_response().getError_code()==100110){//重新授权流程
                       throw new ServerException("授权失效", responsebean.getError_response().getError_code()+"");
                   }
             }

        }else{
            response = response.newBuilder()
                    .body(ResponseBody.create(null, resp))
                    .build();
        }
        return response;
    }


    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    private Request addParam(Request oldRequest,String refresh_token) {

        HttpUrl.Builder builder = oldRequest.url()
                .newBuilder()
                .setEncodedQueryParameter("refresh_token", refresh_token);

        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build();

        return newRequest;
    }

}
