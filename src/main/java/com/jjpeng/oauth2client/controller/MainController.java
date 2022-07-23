package com.jjpeng.oauth2client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author JJPeng
 * @date 2022/7/23 12:07
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String main() {
        return "main.html";
    }
}
