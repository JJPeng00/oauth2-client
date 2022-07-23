package com.jjpeng.oauth2client.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

/**
 * @author JJPeng
 * @date 2022/7/23 12:07
 */
@Controller
public class MainController {

    private Logger logger = Logger.getLogger(MainController.class.getName());

    @GetMapping("/")
    public String main(OAuth2AuthenticationToken token) {
        //认证之后，认证过滤器将用户的信息存储到Security Context中
        logger.info(String.valueOf(token.getPrincipal()));
        return "main.html";
    }
}
