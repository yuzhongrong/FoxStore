package foxstore.android.com.foxstore.model.params;

import foxstore.android.com.common.widgets.DuoDuoSignTools;

/**这个接口无需签名*/
public class ReqAccessTokenParam {
    private  String grant_type;
    private  String code;
    protected String client_id="f50ccf86e3c34ddf934fcec1f129193f";
    protected String client_secret="44bd6a7f915c9a15612125f878fbcf2d4e7e29d8";
    protected String redirect_uri="https://www.bmob.cn/";

    protected String refresh_token;

    public ReqAccessTokenParam(String grant_typ, String code,String refresh_token) {
        this.grant_type = grant_typ;
        this.code = code;
        this.refresh_token=refresh_token;
    }

}
