package com.example.administrator.oisee.bean.Remen;

import java.util.List;

public class JsonRootBean {
    /**
     * Copyright 2018 bejson.com
     */

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
