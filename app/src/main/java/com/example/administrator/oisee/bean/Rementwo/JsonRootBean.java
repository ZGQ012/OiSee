package com.example.administrator.oisee.bean.Rementwo;

import java.util.List;

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
