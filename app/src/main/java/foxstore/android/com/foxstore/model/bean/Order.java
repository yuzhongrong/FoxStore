package foxstore.android.com.foxstore.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yuzhongrong on 2018/6/30.
 */

public class Order extends BmobObject {
    String userid;//用户id
    String url;//主图
    boolean iscan;//是否参团
    int count;//叠加个数
    String keyword;//关键词
    String storename;//店铺名称
    String price;//单价
    private String goods_id;

    public Order(String userid,String goods_id, String url, boolean iscan, int count, String keyword, String storename, String price) {
        this.userid = userid;
        this.url = url;
        this.iscan = iscan;
        this.count = count;
        this.keyword = keyword;
        this.storename = storename;
        this.price = price;
        this.goods_id=goods_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIscan() {
        return iscan;
    }

    public void setIscan(boolean iscan) {
        this.iscan = iscan;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
