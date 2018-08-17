package com.zts.educational.admin.controller;

import com.zts.educational.admin.model.dto.SystemDto;
import com.zts.educational.admin.rpc.MenuConsumer;
import com.zts.educational.admin.rpc.SystemConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class IndexController
 *
 * @author 张麒 2018-8-17.
 * @version Description:
 */
@Controller
public class IndexController {

    @Autowired
    private MenuConsumer menuConsumer;

    @Autowired
    private SystemConsumer systemConsumer;

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        // 获取系统信息
        SystemDto system = systemConsumer.findSystemMenu();
        // 设置系统信息
        model.put("system", system);
        // 设置菜单信息
        model.put("menus", system.getChild());
//        CookieHelper helper = CookieHelper.getInstance(request, response);
        // 获取重定向URL
//        String form_url = helper.getCookieValue(SSOConstants.PARAM_FROM_URL);
        // 重定向URL不等于空，并且不是登录页面URL
//        if (StringUtil.isNotBlank(form_url) && !(form_url = EncrypterTool.decrypt(EncrypterTool.Security.Des3ECB, form_url)).equals(properties.getLoginUrl())) {
//            helper.clearCookie(SSOConstants.PARAM_FROM_URL);
//            model.put("url", form_url);
//            // 菜单列表选中效果
//            for (MenuDto menu : system.getChild()) {
//                for (MenuDto child : menu.getChild()) {
//                    if (form_url.contains(child.getUrl())) {
//                        menu.setChoose(true);
//                        child.setChoose(true);
//                        break;
//                    }
//                }
//            }
//        }
        // 用户session信息
//        model.put(SSOConstants.CONST_UAMS_ASSERTION, SessionHolder.getSessionInfo().getUserAuthData());
        return "index";
    }
}
