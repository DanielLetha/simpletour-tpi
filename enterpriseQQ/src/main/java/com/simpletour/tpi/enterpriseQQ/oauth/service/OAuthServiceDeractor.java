package com.simpletour.tpi.enterpriseQQ.oauth.service;

import com.simpletour.tpi.enterpriseQQ.entity.OAuthUser;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;


public abstract class OAuthServiceDeractor implements OAuthService {

    private final OAuthService oAuthService;
    private final String oAuthType;
    private final String authorizationUrl;
    
    public OAuthServiceDeractor(OAuthService oAuthService, String type) {
        super();
        this.oAuthService = oAuthService;
        this.oAuthType = type;
        this.authorizationUrl = oAuthService.getAuthorizationUrl(null);
    }

    public Token getRequestToken() {
        return oAuthService.getRequestToken();
    }

    public Token getAccessToken(Token token, Verifier verifier) {
        return oAuthService.getAccessToken(token, verifier);
    }

    public void signRequest(Token token, OAuthRequest oAuthRequest) {
        oAuthService.signRequest(token, oAuthRequest);

    }

    public String getVersion() {
        return oAuthService.getVersion();
    }

    public String getAuthorizationUrl(Token token) {
        return oAuthService.getAuthorizationUrl(token);
    }

    public String   getoAuthType() {
        return oAuthType;
    }
    
    public String getAuthorizationUrl(){
        return authorizationUrl;
    }
    
    public abstract OAuthUser getOAuthUser(Token accessToken);

}
