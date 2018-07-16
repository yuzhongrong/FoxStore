package foxstore.android.com.foxstore.iprovider;

import com.alibaba.android.arouter.facade.template.IProvider;

import java.util.Map;

import io.reactivex.Flowable;

public interface DuoDuoGoodsProviderServices extends IProvider {

    Flowable<String> getGoodsLists(Map<String,String> reqGoodsListsParams);
    Flowable<String> getCommonData(Map<String,String> reqGoodsListsParams);
}
