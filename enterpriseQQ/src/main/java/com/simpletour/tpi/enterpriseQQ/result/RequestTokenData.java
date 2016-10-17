package com.simpletour.tpi.enterpriseQQ.result;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/17 18:33
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class RequestTokenData {
    private String access_token;

    private String expires_in;

    private String open_id;

    private String refresh_token;

    private String company_token;

    private String company_id;

    private String state;

    public RequestTokenData() {
    }

    public RequestTokenData(String access_token, String expires_in, String open_id, String refresh_token, String company_token, String company_id, String state) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.open_id = open_id;
        this.refresh_token = refresh_token;
        this.company_token = company_token;
        this.company_id = company_id;
        this.state = state;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getCompany_token() {
        return company_token;
    }

    public void setCompany_token(String company_token) {
        this.company_token = company_token;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
