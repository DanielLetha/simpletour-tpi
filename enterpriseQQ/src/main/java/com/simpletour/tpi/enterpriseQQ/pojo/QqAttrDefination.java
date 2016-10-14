//package com.simpletour.tpi.enterpriseQQ.pojo;
//
//import org.pac4j.core.profile.converter.Converters;
//import org.pac4j.oauth.profile.OAuthAttributesDefinition;
//
///**
// * @Brief :  ${用途}
// * @Author: liangfei/liangfei@simpletour.com
// * @Date :  2016/10/14 15:22
// * @Since ： ${VERSION}
// * @Remark: ${Remark}
// */
//public class QqAttrDefination extends OAuthAttributesDefinition {
//
//    public static final String ID = "id";                            //id。数据库业务id
//    public static final String OPEN_ID = "openid";                    //openid。
//    public static final String NICK_NAME = "nickname";                //用户在昵称。
//    public static final String GENDER = "gender";                    //性别。 如果获取不到则默认返回"男"
//    public static final String PROVINCE = "province";                //省
//    public static final String CITY = "city";                        //市
//    public static final String FIGUREURL_QQ_1 = "figureurl_qq_1";    //大小为40×40像素的QQ头像URL。
//    public static final String FIGUREURL_QQ_2 = "figureurl_qq_2";    //大小为100×100像素的QQ头像URL 不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有
//
//    public QqAttrDefination() {
//        addAttribute(ID, Converters.longConverter);
//        addAttribute(OPEN_ID, Converters.stringConverter);
//        addAttribute(NICK_NAME, Converters.stringConverter);
//        addAttribute(GENDER, Converters.stringConverter);
//        addAttribute(PROVINCE, Converters.stringConverter);
//        addAttribute(CITY, Converters.stringConverter);
//        addAttribute(FIGUREURL_QQ_1, Converters.stringConverter);
//        addAttribute(FIGUREURL_QQ_2, Converters.stringConverter);
//    }
//
//
//}
