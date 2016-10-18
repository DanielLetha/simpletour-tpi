package com.simpletour.tpi.enterpriseQQ.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simpletour.tpi.enterpriseQQ.QqApp;
import com.simpletour.tpi.enterpriseQQ.oauth.service.OAuthServices;
import com.simpletour.tpi.enterpriseQQ.result.AuthorizeResult;
import com.simpletour.tpi.enterpriseQQ.result.ResultBean;
import com.simpletour.tpi.enterpriseQQ.result.ResultHeader;
import com.simpletour.tpi.enterpriseQQ.entity.User;
import com.simpletour.tpi.enterpriseQQ.util.HttpUtil;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/14 15:43
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
@SpringApplicationConfiguration(QqApp.class)
@Controller
public class QqOauthController {

    @Autowired
    OAuthServices oAuthServices;

    @Value("${oAuth.qq.app_id}")
    String qqAppId;

    @Value("${oAuth.qq.app_secret}")
    String qqAppSecret;

    @Value("${demo.callback}")
    String callback;

    @Value("${oAuth.qq.state}")
    String state;

    private String code;

    private String companyToken;
    private String companyId;
    private String employeeToken;
    private String openId;




    /**
     * 在管理中心开通功能的时候，用于回调获取company_code,以及反馈腾讯回调地址可用
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    @ResponseBody
    public ResultBean redirect_exam(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("code", code);
        System.out.println("code:" + code);
        return new ResultBean(0);
    }

    /**
     * 员工登录获取用户的code
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("oAuthServices", oAuthServices.getAllOAuthServices());
        return "index";
    }

    /**
     * 获取公司token
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/companyToken"}, method = RequestMethod.GET)
    @ResponseBody
    public String companyToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getSession().getAttribute("code") != null){
            //从session中获取company的code
            code = request.getSession().getAttribute("code").toString();
            //根据code获取公司token
            String companyAtokenUrl = "https://openapi.b.qq.com/oauth2/companyToken?grant_type=authorization_code&code=" + code + "&app_id=" + qqAppId + "&app_secret=" + qqAppSecret + "&state=" + state + "&redirect_uri=" + callback;
            String companyTokenRet = HttpUtil.httpGet(companyAtokenUrl);//返回带token的json字符串
            AuthorizeResult companyToken = JSON.parseObject(companyTokenRet, AuthorizeResult.class);
            String companyAccessToken = companyToken.getData().getCompany_token();//取出token
            String companyId = companyToken.getData().getCompany_id();
            request.getSession().setAttribute("companyAccessToken", companyAccessToken);
            request.getSession().setAttribute("companyId", companyId);
        }else {
            request.getSession().setAttribute("companyAccessToken", "6a25b2a3b5c8eec5920ecae14396ad8b");
            request.getSession().setAttribute("companyId", "3d25c3048adcd25a6ac42b5ceb57640a");
        }
        return null;
    }

    /**
     * 获取员工token
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/employeeToken", method = RequestMethod.GET)
    @ResponseBody
    public String employeeToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //从session中获取用户的code
        if(request.getSession().getAttribute("code") != null){
            code = request.getSession().getAttribute("code").toString();
            //根据code员工token
            String employeeTokenUrl = "https://openapi.b.qq.com/oauth2/token?grant_type=authorization_code&code=" + code + "&app_id=" + qqAppId + "&app_secret=" + qqAppSecret + "&state=" + state + "&redirect_uri=" + callback;
            String employeeTokenRet = HttpUtil.httpGet(employeeTokenUrl);//返回带token的json字符串
            AuthorizeResult employeeToken = JSON.parseObject(employeeTokenRet, AuthorizeResult.class);
            String employeeAccessToken = employeeToken.getData().getCompany_token();//取出token
            String openId = employeeToken.getData().getOpen_id();
            request.getSession().setAttribute("employeeToken", employeeAccessToken);
            request.getSession().setAttribute("openId", openId);
        }else {
            request.getSession().setAttribute("openId", "84b0a1d132567c4889be7c3b03316a57");

        }

        return null;
    }

    /**
     * 发送TIPS消息
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = {"/sendTips"}, method = RequestMethod.GET)
    @ResponseBody
    public String sendTips(HttpServletRequest request, HttpServletResponse response) throws IOException {
        companyToken = request.getSession().getAttribute("companyAccessToken").toString();
        companyId = request.getSession().getAttribute("companyId").toString();
        openId = request.getSession().getAttribute("openId").toString();
        //发送消息
        String sendTipsUrl = "https://openapi.b.qq.com/api/tips/send?company_id="+companyId+"&company_token="+companyToken+"&app_id=" + qqAppId + "&client_ip=192.168.4.123&oauth_version=2&to_all=0&receivers="+openId+"&window_title=Test&tips_title=Test&tips_content=Hello Daniel&tips_url=http://www.simpletour.com";
        String sendTipsRet = HttpUtil.httpGet(sendTipsUrl);//返回带token的json字符串
        ResultHeader sendTipsResult = JSON.parseObject(sendTipsRet, ResultHeader.class);
        return sendTipsResult.getRet().toString();
    }
}
