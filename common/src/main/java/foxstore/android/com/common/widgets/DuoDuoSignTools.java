package foxstore.android.com.common.widgets;

import android.text.TextUtils;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.base.application.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import foxstore.android.com.common.bean.AccessTokenBean;
import foxstore.android.com.common.kes.AcacheKeys;

/**
 * Created by yuzhongrong on 2018/6/19.
 */

public class DuoDuoSignTools {


    public static String client_id="f50ccf86e3c34ddf934fcec1f129193f";
    public static String client_secret="44bd6a7f915c9a15612125f878fbcf2d4e7e29d8";
    public static String redirect_uri="https://www.bmob.cn/";

    public static Map<String, String> getDefaultParams(){
       AccessTokenBean bean= (AccessTokenBean) ACacheUtil.get(BaseApplication.getContext()).getAsObject(AcacheKeys.Token);
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("client_id", "" +client_id);
        paramsmap.put("timestamp", "" + System.currentTimeMillis());//默认有时间戳\
        if(bean!=null){
            paramsmap.put("access_token",bean.getAccess_token());
        }
        paramsmap.put("redirect_uri",redirect_uri);
        return paramsmap;
    }




    /**
     * 签名
     * @param params
     * @return
     */
    public static Map<String,String> sign(Map<String, String> params) {
        //对key进行字典升序排序
        Collection<String> keyset = params.keySet();
        List<String> list = new ArrayList<>(keyset);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder(client_secret);
        for (String key : list) {
            String value = params.get(key);
            if (!TextUtils.isEmpty(value)) {
                sb.append(key).append(value);
            }
        }
        sb.append(client_secret);
       String sign= md5(sb.toString()).toUpperCase();
       params.put("sign",sign);
        return params;
    }



    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
