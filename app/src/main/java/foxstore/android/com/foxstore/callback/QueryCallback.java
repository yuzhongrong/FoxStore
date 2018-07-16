package foxstore.android.com.foxstore.callback;

import org.json.JSONArray;

import java.util.List;


/**
 * Created by yuzhongrong on 2018/5/26.
 */

public class QueryCallback<T>  {

    public  void onSuccess(List<T> datas){};
    public  void onFail(){};

}
