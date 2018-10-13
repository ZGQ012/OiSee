package com.example.administrator.oisee.bean;


/**
 * Copyright 2018 bejson.com
 */

/**
 * Auto-generated: 2018-09-17 12:51:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ZhuceSmScode {

    private int ErrCode;
    private String ErrMsg;
    private SmS Response;

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

    public void setSms(SmS SmS) {
        this.Response= SmS;
    }

    public SmS getSms() {
        return Response;
    }


}
