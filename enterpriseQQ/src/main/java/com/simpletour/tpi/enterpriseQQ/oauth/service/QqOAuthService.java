package com.simpletour.tpi.enterpriseQQ.oauth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.simpletour.tpi.enterpriseQQ.entity.OAuthUser;
import com.simpletour.tpi.enterpriseQQ.entity.User;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class QqOAuthService extends OAuthServiceDeractor {

    private static final String PROTECTED_RESOURCE_URL = "https://openapi.b.qq.com/api/tips/send";

    public QqOAuthService(OAuthService oAuthService) {
        super(oAuthService, "qq");
    }

    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        this.signRequest(accessToken, request);
        Response response = request.send();
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setoAuthType(getoAuthType());
        Object result = JSON.parse(response.getBody());
        oAuthUser.setoAuthId(JSONPath.eval(result, "$.id").toString());
        oAuthUser.setUser(new User());
        oAuthUser.getUser().setUsername(JSONPath.eval(result, "$.login").toString());
        return oAuthUser;
    }
    

}
