package com.example.administrator.oisee.bean;


import java.io.Serializable;

public class LoginBean implements Serializable {

    private int ErrCode;
    private String ErrMsg;
    private LoginRe Response;

    public void setErrCode(int ErrCode) {
        this.ErrCode = ErrCode;
    }

    public int getErrCode() {
        return ErrCode;
    }

    public void setErrMsg(String ErrMsg) {
        this.ErrMsg = ErrMsg;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setResponse(LoginRe Response) {
        this.Response = Response;
    }

    public LoginRe getResponse() {
        return Response;
    }


}
