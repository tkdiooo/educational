package com.zts.educational.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class LoginController
 *
 * @author 张麒 2018-8-17.
 * @version Description:
 */
@Controller
public class LoginController {


    @GetMapping("login")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }
}
