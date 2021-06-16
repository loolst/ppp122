package com.wenna4.ppp.Intf.DO;

public class ResponseDO {
    private boolean success;
    private String msg;
    private int errorCode;
    private Object data;

    public ResponseDO() {
    }

    public ResponseDO(boolean success, String msg, int errorCode, Object data) {
        this.success = success;
        this.msg = msg;
        this.errorCode = errorCode;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDO{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", errorCode=" + errorCode +
                ", data=" + data +
                '}';
    }
}
