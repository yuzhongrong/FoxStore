package foxstore.android.com.common.bean;

import java.io.Serializable;
import java.util.List;

public class AccessTokenBean implements Serializable{
    /**
     * scope : ["pdd.goods.add","pdd.virtual.mobile.charge.notify","pdd.logistics.address.get","pdd.refund.address.list.get","pdd.promotion.goods.coupon.list.get","pdd.logistics.online.send","pdd.goods.country.get","pdd.goods.sale.status.set","pdd.goods.detail.get","pdd.goods.sku.stock.update","pdd.goods.authorization.cats","pdd.goods.logistics.template.get","pdd.ad.chart.bykeyword.get","pdd.goods.fabric.content.get","pdd.order.information.get","pdd.goods.spec.id.get","pdd.goods.category.classify","pdd.order.number.list.get","pdd.logistics.companies.get","pdd.order.status.get","pdd.promotion.coupon.quantity.add","pdd.promotion.merchant.coupon.list.get","pdd.goods.information.get","pdd.order.number.list.increment.get","pdd.goods.cats.get","pdd.goods.image.upload","pdd.promotion.coupon.close","pdd.goods.fabric.get","pdd.goods.spec.get","pdd.goods.logistics.template.create","pdd.promotion.goods.coupon.create","pdd.goods.list.get","pdd.refund.status.check","pdd.promotion.home.coupon.create","pdd.goods.sku.stock.increment.update","pdd.goods.commit.detail.get","pdd.goods.information.update","pdd.refund.list.increment.get","pdd.order.list.get"]
     * access_token : bb92c948d26b4ad6a0d196af17453af054ac82cf
     * expires_in : 86399
     * refresh_token : 8dd532daa6da4e29bb6815af28740780733a2fff
     * owner_id : 877707
     * owner_name : pdd8777073791
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String owner_id;
    private String owner_name;
    private List<String> scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }
}
