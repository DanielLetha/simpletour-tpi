package com.simpletour.tpi.enterpriseQQ.oauth.api;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/14 14:20
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class QqApi extends DefaultApi20 {

    private static final String AUTHORIZE_URL = "https://openapi.b.qq.com/oauth2/authorize?response_type=code&app_id=%s&oauth_version=2&redirect_uri=%s&state=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";
    private static final String ACCESS_TOKEN_URL = "https://openapi.b.qq.com/oauth2/token?state=%s";

    private final String qqState;

    public QqApi(String qqState) {
        this.qqState = qqState;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return String.format(ACCESS_TOKEN_URL,qqState);
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(),
                "Must provide a valid url as callback. 	QQ does not support OOB");

        if (config.hasScope()) {
            return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),qqState,
                    OAuthEncoder.encode(config.getScope()));
        } else {
            return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),qqState);
        }
    }



}
