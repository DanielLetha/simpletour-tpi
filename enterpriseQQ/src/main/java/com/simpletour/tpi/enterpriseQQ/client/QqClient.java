//package com.simpletour.tpi.enterpriseQQ.client;
//
//import com.simpletour.tpi.enterpriseQQ.profile.QqProfile;
//import org.pac4j.core.client.BaseClient;
//import org.pac4j.core.context.WebContext;
//import org.pac4j.oauth.client.BaseOAuth20Client;
//import org.pac4j.oauth.credentials.OAuthCredentials;
//import org.scribe.model.Token;
//
///**
// * @Brief :  ${用途}
// * @Author: liangfei/liangfei@simpletour.com
// * @Date :  2016/10/14 15:15
// * @Since ： ${VERSION}
// * @Remark: ${Remark}
// */
//public class QqClient extends BaseOAuth20Client<QqProfile>{
//
//    @Override
//    protected boolean hasBeenCancelled(WebContext webContext) {
//        return false;
//    }
//
//    @Override
//    protected String getProfileUrl(Token token) {
//        return null;
//    }
//
//    @Override
//    protected QqProfile extractUserProfile(String s) {
//        return null;
//    }
//
//    @Override
//    protected BaseClient<OAuthCredentials, QqProfile> newClient() {
//        return null;
//    }
//}
