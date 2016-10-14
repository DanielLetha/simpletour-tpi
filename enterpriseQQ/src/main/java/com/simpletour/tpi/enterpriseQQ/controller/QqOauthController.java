package com.simpletour.tpi.enterpriseQQ.controller;

import com.simpletour.tpi.enterpriseQQ.QqApp;
import com.simpletour.tpi.enterpriseQQ.entity.OAuthUser;
import com.simpletour.tpi.enterpriseQQ.entity.User;
import com.simpletour.tpi.enterpriseQQ.oauth.service.OAuthServiceDeractor;
import com.simpletour.tpi.enterpriseQQ.oauth.service.OAuthServices;
import com.simpletour.tpi.enterpriseQQ.repository.OauthUserRepository;
import com.simpletour.tpi.enterpriseQQ.repository.UserRepository;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    OauthUserRepository oauthUserRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = {"","/login"},method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("oAuthServices", oAuthServices.getAllOAuthServices());
        return "index";
    }
    @RequestMapping(value = "/oauth/{type}/callback", method=RequestMethod.GET)
    public String claaback(@RequestParam(value = "code", required = true) String code,
                           @PathVariable(value = "type") String type,
                           HttpServletRequest request, Model model){
        OAuthServiceDeractor oAuthService = oAuthServices.getOAuthService(type);
        Token accessToken = oAuthService.getAccessToken(null, new Verifier(code));
        OAuthUser oAuthInfo = oAuthService.getOAuthUser(accessToken);
        OAuthUser oAuthUser = oauthUserRepository.findByOAuthTypeAndOAuthId(oAuthInfo.getoAuthType(),
                oAuthInfo.getoAuthId());
        if(oAuthUser == null){
            model.addAttribute("oAuthInfo", oAuthInfo);
            return "register";
        }
        request.getSession().setAttribute("oauthUser", oAuthUser);
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
        if(userRepository.findByUsername(user.getUsername()) != null){
            model.addAttribute("errorMessage", "用户名已存在");
            model.addAttribute("oAuthInfo", oAuthInfo);
            return "register";
        }
        user = userRepository.save(user);
        OAuthUser oAuthUser = oauthUserRepository.findByOAuthTypeAndOAuthId(oAuthType, oAuthId);
        if(oAuthUser == null){
            oAuthInfo.setUser(user);
            oAuthUser = oauthUserRepository.save(oAuthInfo);
        }
        request.getSession().setAttribute("oauthUser", user);
        return "redirect:/success";
    }

    @RequestMapping(value = "/success", method=RequestMethod.GET)
    @ResponseBody
    public Object success(HttpServletRequest request){
        return request.getSession().getAttribute("oauthUser");
    }

}
