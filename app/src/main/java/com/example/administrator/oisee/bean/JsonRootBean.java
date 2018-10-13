package com.example.administrator.oisee.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-09-14 17:29:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private int ErrCode;
    private String ErrMsg;
    private List<Response> Response;
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

    public void setResponse(List<Response> Response) {
        this.Response = Response;
    }
    public List<Response> getResponse() {
        return Response;
    }

}