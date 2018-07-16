package foxstore.android.com.foxstore.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by yuzhongrong on 2018/5/26.
 */

public class article extends BmobObject {


    /**
     * bigimg : {"__type":"File","cdn":"upyun","filename":"pen.png","url":"http://bmob-cdn-12859.b0.upaiyun.com/2018/05/26/d08c5dc5409048538021b25a673ae861.png"}
     * buycount : 1306
     * createdAt : 2018-05-26 18:57:30
     * objectId : U3WL111D
     * price : 100
     * subtitle : 自定义键盘，实现公司定制化业务
     * title : 自定义键盘
     * updatedAt : 2018-05-26 19:09:51
     */

    private BigimgBean bigimg;
    private String buycount;
    private int price;
    private String subtitle;
    private String title;


    public BigimgBean getBigimg() {
        return bigimg;
    }

    public void setBigimg(BigimgBean bigimg) {
        this.bigimg = bigimg;
    }

    public String getBuycount() {
        return buycount;
    }

    public void setBuycount(String buycount) {
        this.buycount = buycount;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class BigimgBean {
        /**
         * __type : File
         * cdn : upyun
         * filename : pen.png
         * url : http://bmob-cdn-12859.b0.upaiyun.com/2018/05/26/d08c5dc5409048538021b25a673ae861.png
         */

        private String __type;
        private String cdn;
        private String filename;
        private String url;

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
