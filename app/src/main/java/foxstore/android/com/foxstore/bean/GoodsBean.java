package foxstore.android.com.foxstore.bean;

import java.util.List;

public class GoodsBean {


    /**
     * goods_list_get_response : {"total_count":1,"goods_list":[{"goods_id":2177352330,"goods_name":"超长待机防水运动休闲商务蓝牙耳机耳塞迷你苹果oppo vivo 通用","image_url":"","is_more_sku":1,"goods_quantity":3000,"is_onsale":1,"sku_list":[{"spec":"s2玫瑰红","sku_id":41447963302,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1},{"spec":"S2经典黑","sku_id":41447963301,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1},{"spec":"s2商务蓝","sku_id":41448791362,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1}]}]}
     */

    private GoodsListGetResponseBean goods_list_get_response;

    public GoodsListGetResponseBean getGoods_list_get_response() {
        return goods_list_get_response;
    }

    public void setGoods_list_get_response(GoodsListGetResponseBean goods_list_get_response) {
        this.goods_list_get_response = goods_list_get_response;
    }

    public static class GoodsListGetResponseBean {
        /**
         * total_count : 1
         * goods_list : [{"goods_id":2177352330,"goods_name":"超长待机防水运动休闲商务蓝牙耳机耳塞迷你苹果oppo vivo 通用","image_url":"","is_more_sku":1,"goods_quantity":3000,"is_onsale":1,"sku_list":[{"spec":"s2玫瑰红","sku_id":41447963302,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1},{"spec":"S2经典黑","sku_id":41447963301,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1},{"spec":"s2商务蓝","sku_id":41448791362,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1}]}]
         */

        private int total_count;
        private List<GoodsListBean> goods_list;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean {
            /**
             * goods_id : 2177352330
             * goods_name : 超长待机防水运动休闲商务蓝牙耳机耳塞迷你苹果oppo vivo 通用
             * image_url :活动主图
             * is_more_sku : 1
             * goods_quantity : 3000
             * is_onsale : 1
             * sku_list : [{"spec":"s2玫瑰红","sku_id":41447963302,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1},{"spec":"S2经典黑","sku_id":41447963301,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1},{"spec":"s2商务蓝","sku_id":41448791362,"sku_quantity":1000,"outer_id":"","outer_goods_id":null,"is_sku_onsale":1}]
             */

            private long goods_id;
            private String goods_name;
            private String image_url;
            private int is_more_sku;
            private int goods_quantity;
            private int is_onsale;
            private List<SkuListBean> sku_list;

            public long getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(long goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public int getIs_more_sku() {
                return is_more_sku;
            }

            public void setIs_more_sku(int is_more_sku) {
                this.is_more_sku = is_more_sku;
            }

            public int getGoods_quantity() {
                return goods_quantity;
            }

            public void setGoods_quantity(int goods_quantity) {
                this.goods_quantity = goods_quantity;
            }

            public int getIs_onsale() {
                return is_onsale;
            }

            public void setIs_onsale(int is_onsale) {
                this.is_onsale = is_onsale;
            }

            public List<SkuListBean> getSku_list() {
                return sku_list;
            }

            public void setSku_list(List<SkuListBean> sku_list) {
                this.sku_list = sku_list;
            }

            public static class SkuListBean {
                /**
                 * spec : s2玫瑰红
                 * sku_id : 41447963302
                 * sku_quantity : 1000
                 * outer_id :
                 * outer_goods_id : null
                 * is_sku_onsale : 1
                 */

                private String spec;
                private long sku_id;
                private int sku_quantity;
                private String outer_id;
                private Object outer_goods_id;
                private int is_sku_onsale;

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
                }

                public long getSku_id() {
                    return sku_id;
                }

                public void setSku_id(long sku_id) {
                    this.sku_id = sku_id;
                }

                public int getSku_quantity() {
                    return sku_quantity;
                }

                public void setSku_quantity(int sku_quantity) {
                    this.sku_quantity = sku_quantity;
                }

                public String getOuter_id() {
                    return outer_id;
                }

                public void setOuter_id(String outer_id) {
                    this.outer_id = outer_id;
                }

                public Object getOuter_goods_id() {
                    return outer_goods_id;
                }

                public void setOuter_goods_id(Object outer_goods_id) {
                    this.outer_goods_id = outer_goods_id;
                }

                public int getIs_sku_onsale() {
                    return is_sku_onsale;
                }

                public void setIs_sku_onsale(int is_sku_onsale) {
                    this.is_sku_onsale = is_sku_onsale;
                }
            }
        }
    }
}
