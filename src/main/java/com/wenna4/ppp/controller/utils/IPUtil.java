package com.wenna4.ppp.controller.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {

    /**
     * 获取用户IP
     *
     * @return
     */
    public static String getRequestIP(HttpServletRequest httpRequest) {
        String ipForwarded = (String) httpRequest.getHeader("x-forwarded-for");
        if (ipForwarded == null) {
            ipForwarded = httpRequest.getRemoteAddr();
        } else {
            String ips[] = ipForwarded.split(",");
            ipForwarded = ips[ips.length - 1].trim();
        }
        if (StringUtils.equals(ipForwarded, "0:0:0:0:0:0:0:1"))
            return "127.0.0.1";
        return ipForwarded;
    }

}
