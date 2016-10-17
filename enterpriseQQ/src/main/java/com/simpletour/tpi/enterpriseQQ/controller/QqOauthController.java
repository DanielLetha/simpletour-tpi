package com.simpletour.tpi.enterpriseQQ.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simpletour.tpi.enterpriseQQ.QqApp;
import com.simpletour.tpi.enterpriseQQ.entity.OAuthUser;
import com.simpletour.tpi.enterpriseQQ.entity.OauthInfo;
import com.simpletour.tpi.enterpriseQQ.result.AuthorizeResult;
import com.simpletour.tpi.enterpriseQQ.result.ResultHeader;
import com.simpletour.tpi.enterpriseQQ.entity.User;
import com.simpletour.tpi.enterpriseQQ.oauth.service.OAuthServiceDeractor;
import com.simpletour.tpi.enterpriseQQ.oauth.service.OAuthServices;
import com.simpletour.tpi.enterpriseQQ.util.HttpUtil;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
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

    @RequestMapping(value = {""},method = RequestMethod.GET)
    @ResponseBody
    public ResultHeader redirect_exam(Model model){
        return new ResultHeader(0);
    }

    @RequestMapping(value = {"/login"},method = RequestMethod.GET)
    public String login(Model model, HttpSession session){
        model.addAttribute("oAuthServices", oAuthServices.getAllOAuthServices());
        return "index";
    }
    @RequestMapping(value = {"/userInfo"},method = RequestMethod.GET)
    @ResponseBody
    public OauthInfo userInfo(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException{
        //根据code获取员工token
        String code=request.getParameter("code");//客户端传回来的授权码
        String atokenUrl="https://openapi.b.qq.com/oauth2/token?grant_type=authorization_code&code="+code+"&app_id="+qqAppId+"&app_secret="+qqAppSecret+"&state="+state+"&redirect_uri="+callback;
        String tokenRet = HttpUtil.httpGet(atokenUrl);//返回带token的json字符串
        AuthorizeResult token = JSON.parseObject(tokenRet,AuthorizeResult.class);
        String openId = token.getData().getOpen_id();

        //根据code获取公司token
        String companyAtokenUrl="https://openapi.b.qq.com/oauth2/companyToken?grant_type=authorization_code&code="+code+"&app_id="+qqAppId+"&app_secret="+qqAppSecret+"&state="+state+"&redirect_uri="+callback;
        String companyTokenRet = HttpUtil.httpGet(companyAtokenUrl);//返回带token的json字符串
        AuthorizeResult companyToken = JSON.parseObject(companyTokenRet,AuthorizeResult.class);
        String companyAccessToken= companyToken.getData().getAccess_token();//取出token
        String companyId = companyToken.getData().getCompany_id();
        //获取用户的信息
        String apiUrl="https://openapi.b.qq.com/user/info?company_id="+companyId+"&company_token="+companyAccessToken+"&app_id="+qqAppId+"&client_ip=192.168.0.0&oauth_version=2&open_ids="+openId;
        String uinfoma=HttpUtil.httpGet(apiUrl);//返回用户信息的字符串
        if(uinfoma!=null) {
            OauthInfo user = null;
            JSONObject oobj = JSON.parseObject(uinfoma);
            String sex = (String) oobj.get("gender");
            String figureurl_qq_1 = (String) oobj.get("figureurl_qq_1");
            String username = (String) oobj.get("nickname");
            user = (OauthInfo) request.getSession().getAttribute("user");
                user.setNickName(username);
                user.setGender(sex);
                user.setFigureurlQq1(figureurl_qq_1);
                request.getSession().setAttribute("user", user);

            return user;
        }
        return null;
    }

    /**
     * 发送TIPS消息
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = {"/sendTips"},method = RequestMethod.GET)
    @ResponseBody
    public String sendTips(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException{
        //根据code获取员工token
        String code=request.getParameter("code");//客户端传回来的授权码

        //根据code获取公司token
        String companyAtokenUrl="https://openapi.b.qq.com/oauth2/companyToken?grant_type=authorization_code&code="+code+"&app_id="+qqAppId+"&app_secret="+qqAppSecret+"&state="+state+"&redirect_uri="+callback;
        String companyTokenRet = HttpUtil.httpGet(companyAtokenUrl);//返回带token的json字符串
        AuthorizeResult companyToken = JSON.parseObject(companyTokenRet,AuthorizeResult.class);
        String companyAccessToken= companyToken.getData().getAccess_token();//取出token
        String companyId = companyToken.getData().getCompany_id();

        //发送消息
        String sendTipsUrl="https://openapi.b.qq.com/api/tips/send?company_id="+companyId+"&company_token="+companyAccessToken+"&app_id="+qqAppId+"&client_ip=192.168.4.123&oauth_version=2&to_all=1&receivers=122&window_title=test&tips_title=test&tips_content=test&tips_url=www.test.com";
        String sendTipsRet = HttpUtil.httpGet(sendTipsUrl);//返回带token的json字符串
        ResultHeader sendTipsResult = JSON.parseObject(sendTipsRet,ResultHeader.class);


        return sendTipsResult.getRet().toString();
    }
    @RequestMapping(value = "/oauth/{type}/callback", method=RequestMethod.GET)
    public String claaback(@RequestParam(value = "code", required = true) String code,
                           @PathVariable(value = "type") String type,
                           HttpServletRequest request, Model model){
        OAuthServiceDeractor oAuthService = oAuthServices.getOAuthService(type);
        Token accessToken = oAuthService.getAccessToken(null, new Verifier(code));
        OAuthUser oAuthInfo = oAuthService.getOAuthUser(accessToken);
//        OAuthUser oAuthUser = oauthUserRepository.findByOAuthTypeAndOAuthId(oAuthInfo.getoAuthType(),
//                oAuthInfo.getoAuthId());
        if(oAuthInfo == null){
            model.addAttribute("oAuthInfo", oAuthInfo);
            return "register";
        }
        request.getSession().setAttribute("oauthUser", oAuthInfo);
        return "redirect:/success";
    }

    @RequestMapping(value = "/register", method=RequestMethod.POST)
    public String register(Model model, User user,
                           @RequestParam(value = "oAuthType", required = false, defaultValue = "") String oAuthType,
                           @RequestParam(value = "oAuthId", required = true, defaultValue = "") String oAuthId,
                           HttpServletRequest request){
        OAuthUser oAuthInfo = new OAuthUser();
        oAuthInfo.setoAuthId(oAuthId);
        oAuthInfo.setoAuthType(oAuthType);
//        if(userRepository.findByUsername(user.getUsername()) != null){
        if(oAuthInfo != null){
            model.addAttribute("errorMessage", "用户名已存在");
            model.addAttribute("oAuthInfo", oAuthInfo);
            return "register";
        }
//        user = userRepository.save(user);
//        OAuthUser oAuthUser = oauthUserRepository.findByOAuthTypeAndOAuthId(oAuthType, oAuthId);
//        if(oAuthUser == null){
//            oAuthInfo.setUser(user);
//            oAuthUser = oauthUserRepository.save(oAuthInfo);
//        }
        request.getSession().setAttribute("oauthUser", user);
        return "redirect:/success";
    }

    @RequestMapping(value = "/success", method=RequestMethod.GET)
    @ResponseBody
    public Object success(HttpServletRequest request){
        return request.getSession().getAttribute("oauthUser");
    }
}
