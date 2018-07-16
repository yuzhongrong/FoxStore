package foxstore.android.com.foxstore.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yuzhongrong on 2018/6/30.
 */

public class MainImg extends BmobObject {
    String userid;//用户id
    String url;//用户刷单主图

    public MainImg(String url) {
        this.url = url;
    }

    public MainImg(String userid,String url) {
        this.url = url;
        this.userid=userid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
