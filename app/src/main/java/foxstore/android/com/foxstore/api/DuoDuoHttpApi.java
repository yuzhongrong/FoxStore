package foxstore.android.com.foxstore.api;

import java.util.Map;

import foxstore.android.com.common.widgets.DuoDuoSignTools;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by yuzhongrong on 2018/6/20.
 */

public interface DuoDuoHttpApi {

    //授权url 拼接
    public static final String auth_url="http://mms.pinduoduo.com/open.html?"+

            "response_type=code&client_id="
            + DuoDuoSignTools.client_id
            +"&redirect_uri="
            +DuoDuoSignTools.redirect_uri;


//没有数据的时候加.或者/
    public static final String auth_url1="http://mms.pinduoduo.com/open.html?response_type=code&client_id=f50ccf86e3c34ddf934fcec1f129193f&redirect_uri=https://www.bmob.cn/&authed=true";


    //登录帐号页面
    public static final String auth_login= "http://mms.pinduoduo.com/Pdd.html#/Login?authurl="+auth_url1;

    String reqToken = "/oauth/token";//获取access_token

    public static String post_goods_list="pdd.goods.list.get";

    @POST(reqToken)
    Flowable<String> reqAccessToken(@Body RequestBody requestBody );//获取access_token

    @POST(".")
    Flowable<String> reqGoodsList(@QueryMap Map<String,String> map);

    @POST(".")
    Flowable<String> reqGoodsDetail(@QueryMap Map<String,String> map);

    @POST(".")
    Flowable<String> reqCommonData(@QueryMap Map<String,String> map);


}
