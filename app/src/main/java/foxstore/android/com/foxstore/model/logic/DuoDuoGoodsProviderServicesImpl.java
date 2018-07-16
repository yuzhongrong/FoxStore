package foxstore.android.com.foxstore.model.logic;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.cjwsc.idcm.net.http.HttpUtils;

import java.util.Map;

import foxstore.android.com.common.api.DuoDuoNetWorkApi;
import foxstore.android.com.foxstore.api.DuoDuoHttpApi;
import foxstore.android.com.foxstore.iprovider.DuoDuoGoodsProviderServices;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(path=DuoDuoProviderPath.path_DuoDuoGoodsProviderServices,name = "商品服务")
public class DuoDuoGoodsProviderServicesImpl implements DuoDuoGoodsProviderServices {
    @Override
    public Flowable<String> getGoodsLists(Map<String,String> reqGoodsListsParams) {
        return HttpUtils.getInstance(BaseApplication.getContext())
                .getRetrofitClient()
                .setBaseUrl(DuoDuoNetWorkApi.duobaseurl)
                .builder(DuoDuoHttpApi.class)
                .reqGoodsList(reqGoodsListsParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**多多所有的接口数据都用这一个方法*/
    @Override
    public Flowable<String> getCommonData(Map<String, String> map) {
        return HttpUtils.getInstance(BaseApplication.getContext())
                .getRetrofitClient()
                .setBaseUrl(DuoDuoNetWorkApi.duobaseurl)
                .builder(DuoDuoHttpApi.class)
                .reqCommonData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void init(Context context) {

    }
}
