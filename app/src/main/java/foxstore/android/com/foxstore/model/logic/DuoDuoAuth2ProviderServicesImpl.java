package foxstore.android.com.foxstore.model.logic;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cjwsc.idcm.Utils.JsonUtil;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.cjwsc.idcm.net.http.HttpUtils;

import foxstore.android.com.foxstore.api.DuoDuoHttpApi;
import foxstore.android.com.common.api.DuoDuoNetWorkApi;
import foxstore.android.com.foxstore.iprovider.DuoDuoAuth2ProviderServices;
import foxstore.android.com.foxstore.model.params.ReqAccessTokenParam;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by yuzhongrong on 2018/6/20.
 */
@Route(path=DuoDuoProviderPath.path_DuoDuoAuth2ProviderServices,name = "授权服务")
public class DuoDuoAuth2ProviderServicesImpl implements DuoDuoAuth2ProviderServices{
    private Context mContext;

    @Override
    public Flowable<String> auth2(ReqAccessTokenParam auth2Param) {

        return HttpUtils.getInstance(BaseApplication.getContext())
                .getRetrofitClient()
                .setBaseUrl("http://open-api.pinduoduo.com/oauth/token/")
                .builder(DuoDuoHttpApi.class)
                .reqAccessToken(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JsonUtil.toJson(auth2Param)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    };
    @Override
    public void init(Context context) {
        mContext=context;
    }
}
