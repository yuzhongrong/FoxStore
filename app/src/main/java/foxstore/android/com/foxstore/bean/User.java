package foxstore.android.com.foxstore.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String state;
    private String nickname;



    private String headimg;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
