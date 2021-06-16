package com.wenna4.ppp.controller.utils;

import com.wenna4.ppp.Intf.constant.KeyConstant;
import com.wenna4.ppp.Intf.utils.security.MD5Signature;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qinsong
 */
public class UserUtils {

    /****
     * @author qingsong
     * 返回userId, 如果不存在，返回0
     * @param domain
     * @param request
     * @return
     * @throws Exception
     */
    public static int dealuser(String domain, HttpServletRequest request) throws Exception {
        //1 判断用户有没有登录，如果已经登录，那么直接返回业务页面
        String userIdStr = CookieUtil.getCookieValue(request, domain, "loginId");
        // 1 判空，2 判断md合法值
        if (!StringUtils.isEmpty(userIdStr)) {
            //userId_md5
            String[] users = userIdStr.split("_");
            if (users.length == 2) {
                if (MD5Signature.verify(users[0], users[1], KeyConstant.MD5_KEY)) {
                    System.out.println("already login");
                    return NumberUtils.toInt(users[0]);
                }
            }
        }
        return 0;
    }
}
