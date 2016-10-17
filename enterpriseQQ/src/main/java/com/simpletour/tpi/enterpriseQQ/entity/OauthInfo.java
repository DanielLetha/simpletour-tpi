package com.simpletour.tpi.enterpriseQQ.entity;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/17 17:43
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class OauthInfo {
    public   String id = "id";							//id。数据库业务id
    public   String openId = "openid";					//openid。
    public   String nickName = "nickname";				//用户在昵称。
    public   String gender = "gender";					//性别。 如果获取不到则默认返回"男"
    public   String province = "province";				//省
    public   String city = "city";						//市
    public   String figureurlQq1 = "figureurl_qq_1";	//大小为40×40像素的QQ头像URL。
    public   String figureurlQq2 = "figureurl_qq_2";

    public OauthInfo() {
    }

    public OauthInfo(String id, String openId, String nickName, String gender, String province, String city, String figureurlQq1, String figureurlQq2) {
        this.id = id;
        this.openId = openId;
        this.nickName = nickName;
        this.gender = gender;
        this.province = province;
        this.city = city;
        this.figureurlQq1 = figureurlQq1;
        this.figureurlQq2 = figureurlQq2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1;
    }

    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2;
    }
}
