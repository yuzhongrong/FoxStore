package foxstore.android.com.foxstore.iprovider;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.cjwsc.idcm.net.response.HttpResponse;

import foxstore.android.com.foxstore.model.params.ReqAccessTokenParam;
import io.reactivex.Flowable;

public interface DuoDuoAuth2ProviderServices extends IProvider {

    Flowable<String> auth2(ReqAccessTokenParam auth2Param);
}
