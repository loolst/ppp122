package com.wenna4.ppp.controller;

import com.wenna4.ppp.Intf.DO.ResponseDO;
import com.wenna4.ppp.Intf.Entity.User;
import com.wenna4.ppp.Intf.UserService;
import com.wenna4.ppp.Intf.constant.KeyConstant;
import com.wenna4.ppp.Intf.utils.security.MD5Signature;
import com.wenna4.ppp.controller.utils.CookieUtil;
import com.wenna4.ppp.controller.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/u")
public class LoginController extends BaseController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;


    ///u/login?callback=
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        // 1 判断用户是否已经登录，如果登录了，那么就返回首页

        String callback = request.getParameter("callback");
        callback = StringUtils.isEmpty(callback) ? "http://127.0.0.1:8080" : callback;

        int userId = UserUtils.dealuser(DOMAIN, request);

        if (userId != 0) {
            return "redirect:" + callback;
        }

        model.addAttribute("callback", callback);
        return "login";
    }

    @PostMapping("/login/verify")
    @ResponseBody
    public ResponseDO loginVerify(String userName, String password, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        log.info("login verify, username = :" + userName + " ,password=:" + password);

        //参数
        if (StringUtils.isEmpty(userName)) {
            log.warn("username is empty");
            return new ResponseDO(false, "用户名不正确", FAIL_ID, null);
        }

        if (StringUtils.isEmpty(password)) {
            log.warn("username is password");
            return new ResponseDO(false, "密码不正确", FAIL_ID, null);
        }


        User user = userService.loadByName(userName);
        if (user == null) {
            return new ResponseDO(false, "用户不存在", FAIL_ID, null);
        }
        //签名 验签 md5
        try {
            if (MD5Signature.verify(password, user.getPassword(), KeyConstant.MD5_KEY)) {
                //登录保存， cookie  , jjwt
                String value = user.getId() + "_" + MD5Signature.sign(user.getId() + "", KeyConstant.MD5_KEY);
                CookieUtil.addCookie("loginId", value, DOMAIN, response);
                return new ResponseDO(true, SUCCESS, SUCCESS_ID, null);
            }

        } catch (Exception e) {
            log.error("fai l to verify login", e);
        }
        return new ResponseDO(false, "密码不正确", FAIL_ID, null);

    }

}
