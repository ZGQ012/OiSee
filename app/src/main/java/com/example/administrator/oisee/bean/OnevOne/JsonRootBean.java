package com.example.administrator.oisee.bean.OnevOne;


import java.util.List;

/**
 * Auto-generated: 2018-09-20 14:41:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private int Total;
    private int ErrCode;
    private String ErrMsg;
    private List<Response> Response;
    public void setTotal(int Total) {
        this.Total = Total;
    }
    public int getTotal() {
        return Total;
    }

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