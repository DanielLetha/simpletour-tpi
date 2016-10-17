package com.simpletour.tpi.enterpriseQQ.result;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/17 18:23
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
@XmlRootElement()
public class AuthorizeResult {

    private ResultHeader resultHeader;

    private RequestTokenData data;

    public AuthorizeResult() {

    }

    public AuthorizeResult(ResultHeader resultHeader, RequestTokenData data) {
        this.resultHeader = resultHeader;
        this.data = data;
    }

    public ResultHeader getResultHeader() {
        return resultHeader;
    }

    public void setResultHeader(ResultHeader resultHeader) {
        this.resultHeader = resultHeader;
    }

    public RequestTokenData getData() {
        return data;
    }

    public void setData(RequestTokenData data) {
        this.data = data;
    }
}
