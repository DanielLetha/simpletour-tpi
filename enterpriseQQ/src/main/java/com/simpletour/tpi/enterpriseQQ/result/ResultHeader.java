package com.simpletour.tpi.enterpriseQQ.result;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/17 18:21
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class ResultHeader {

    private Integer ret;

    private String msg;

    public ResultHeader() {
    }
    public ResultHeader(Integer ret) {
        this.ret = ret;
    }

    public ResultHeader(Integer ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
