package foxstore.android.com.foxstore.bean;

public class ResponseError  {


    /**
     * error_response : {"error_code":10019,"error_msg":"access_token已过期"}
     */

    private ErrorResponseBean error_response;

    public ErrorResponseBean getError_response() {
        return error_response;
    }

    public void setError_response(ErrorResponseBean error_response) {
        this.error_response = error_response;
    }

    public static class ErrorResponseBean {
        /**
         * error_code : 10019
         * error_msg : access_token已过期
         */

        private int error_code;
        private String error_msg;

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public String getError_msg() {
            return error_msg;
        }

        public void setError_msg(String error_msg) {
            this.error_msg = error_msg;
        }
    }
}
