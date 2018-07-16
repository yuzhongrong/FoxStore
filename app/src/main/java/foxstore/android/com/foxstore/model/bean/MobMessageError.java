package foxstore.android.com.foxstore.model.bean;

public class MobMessageError  {

    /**
     * detail : 如果当前appkey对应的包名没有通过审核，每天次appkey+包名最多可以发送20条短信
     * description : 当前appkey发送短信的数量超过限额
     * httpStatus : 400
     * error : The App send SMS overrun, because app not audit.
     * status : 476
     */

    private String detail;
    private String description;
    private int httpStatus;
    private String error;
    private int status;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
