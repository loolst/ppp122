package com.wenna4.ppp.controller.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {


    public static void addCookie(String name, String value, String domain, HttpServletResponse response) {
        setCookie(name, value, 864000, domain, response);// setMaxAge以秒为单位
        // 负数表示关闭浏览器就删除cookie
        // 0表示关闭页面删除cookie
    }

    public static void addCookie(String name, String value, int maxAge, String domain, HttpServletResponse response) {
        setCookie(name, value, maxAge, domain, response);
    }

    public static Cookie getCookie(HttpServletRequest request, String domain, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null) return null;
        if (domain == null || domain.equalsIgnoreCase("localhost")) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String domain, String name) {
        Cookie cookie = getCookie(request, domain, name);
        if (cookie == null) {
            return null;
        } else {
            return cookie.getValue();
        }
    }

    public static void removeCookie(String name, String domain, HttpServletResponse response) {
        setCookie(name, null, 0, domain, response);
    }

    public static void setCookie(String name, String value, int maxAge, String domain, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        if (value == null) {
            cookie.setMaxAge(0);
        } else if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
