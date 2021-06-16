/**
 * Dianping.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.wenna4.ppp.Intf.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author zhi.sun
 * @version $Id: ParameterUtil.java, v 0.1 2012-6-29 上午11:36:23 zhi.sun Exp $
 */
public class ParameterUtil {
    /**
     * 获取指定idList
     *
     * @param list
     * @param name
     * @return
     */
    public static <V> List<Integer> getIds(List<V> list, String name) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<Integer> ids = new ArrayList<>();
        for (V v : list) {
            try {
                Field idField = v.getClass().getDeclaredField(name);
                idField.setAccessible(true);
                ids.add(idField.getInt(v));
            } catch (Exception e) {
                continue;
            }
        }
        return ids;

    }

    public static <V> Map<Integer, V> generateMap(List<V> list, String fieldName) {
        Map<Integer, V> map = new HashMap<Integer, V>();
        if (list == null) {
            return map;
        }
        for (V v : list) {
            int id;
            try {
                Field idField = v.getClass().getDeclaredField(fieldName);
                idField.setAccessible(true);
                id = idField.getInt(v);
            } catch (Exception e) {
                continue;
            }
            map.put(id, v);
        }
        return map;
    }

    /**
     * 将Map组装成待签名数据。 待签名的数据必须按照一定的顺序排列 这个是支付宝提供的服务的规范，否则调用支付宝的服务会通不过签名验证
     *
     * @param params
     * @return
     */
    public static String getSignData(Map<String, String> params) {
        StringBuffer content = new StringBuffer();

        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key) || "sign_type".equals(key)
                    || "tuangou_extend".equals(key)) {
                continue;
            }
            String value = (String) params.get(key);
            if (value != null) {
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            } else {
                content.append((i == 0 ? "" : "&") + key + "=");
            }
        }

        return content.toString();
    }

    /**
     * 忽略掉Null的值。
     *
     * @param params
     * @return
     */
    public static String getSignDataIgnoreNull(Map<String, String> params) {
        StringBuffer content = new StringBuffer();

        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        int count = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key)) {
                continue;
            }
            String value = (String) params.get(key);
            if (value != null && !"".equals(value)) {
                content.append((count == 0 ? "" : "&") + key + "=" + value);
                count++;
            }
        }

        return content.toString();
    }

    /**
     * 将Map中的数据组装成url，这里对空值也转化为url
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String mapToUrl(Map<String, String> params, boolean needSort)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        if (needSort)
            Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String value = params.get(keys.get(i));
            if (i == 0) {
                sb.append(keys.get(i) + "=" + URLEncoder.encode(value, "utf-8"));
            } else {
                if (value != null) {
                    sb.append("&" + keys.get(i) + "="
                            + URLEncoder.encode(value, "utf-8"));
                } else {
                    sb.append("&" + keys.get(i) + "=");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将Map中的数据组装成url，这里对空值也转化为url
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String mapToUrlWithoutEncoder(Map<String, String> params, boolean needSort)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        if (needSort)
            Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String value = params.get(keys.get(i));
            if (i == 0) {
                sb.append(keys.get(i) + "=" + value);
            } else {
                if (value != null) {
                    sb.append("&" + keys.get(i) + "=" + value);
                } else {
                    sb.append("&" + keys.get(i) + "=");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 取得URL中的参数值。
     * <p>
     * 如不存在，返回空值。
     * </p>
     *
     * @param url
     * @param name
     * @return
     */
    public static String getParameter(String url, String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        name = name + "=";
        int start = url.indexOf(name);
        if (start < 0) {
            return null;
        }
        start += name.length();
        int end = url.indexOf("&", start);
        if (end == -1) {
            end = url.length();
        }
        return url.substring(start, end);
    }

    /**
     * 将map对象转化为类对象
     *
     * @param params
     * @param valueType
     * @return
     */
    public static Object convertMapToObject(Map<String, String> params,
                                            Class<?> valueType) {
        if (params == null) {
            return null;
        }
        Object instance = null;
        Method[] methods = valueType.getDeclaredMethods();
        try {
            instance = valueType.newInstance();
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(instance, new Object[]{params.get(field)});
                }
            }
        } catch (Exception e) {
            return null;
        }

        return instance;
    }

    //Object转Map
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = field.get(obj);
            if (value == null)
                value = "";
            map.put(keyName, value);
        }
        return map;
    }

    // 得到xml字符串节点内容
    public static String getXmlValue(String xml, String name) {
        if (StringUtils.isBlank(xml) || StringUtils.isBlank(name)) {
            return "";
        }
        int start = xml.indexOf("<" + name + ">");
        start += (name.length() + 2);// 去掉本字符串和"<"、">"的长度
        int end = xml.indexOf("</" + name + ">");
        if (end > start && end <= (xml.length() - name.length() - 2)) {
            return xml.substring(start, end);
        } else {
            return "";
        }
    }

    public static String getXmlValueNull(String xml, String name) {
        if (StringUtils.isBlank(xml) || StringUtils.isBlank(name)) {
            return "";
        }
        int start = xml.indexOf("<" + name + ">");
        start += (name.length() + 2);// 去掉本字符串和"<"、">"的长度
        int end = xml.indexOf("</" + name + ">");
        if (end > start && end <= (xml.length() - name.length() - 2)) {
            return xml.substring(start, end);
        } else {
            return null;
        }
    }

    /**
     * mapToXml 进行了空值的省略，由于采用的是流的形式发送过去的，所以在这里是不需要编码的
     *
     * @param params
     * @param needSort
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String mapToXml(Map<String, String> params, boolean needSort) {
        try {
            if (params == null) {
                return null;
            }
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("<xml>");
            List<String> keys = new ArrayList<String>(params.keySet());
            String val = null;
            if (needSort) {
                Collections.sort(keys);
            }
            for (String key : keys) {
                if (StringUtils.isEmpty(key)) {
                    continue;
                }
                val = params.get(key);
                if (StringUtils.isEmpty(val)) {
                    continue;
                }
                strBuilder.append("\n");
                strBuilder.append("<").append(key).append(">");
                strBuilder.append("<![CDATA[" + val + "]]>");
                strBuilder.append("</").append(key).append(">");
            }
            strBuilder.append("\n");
            strBuilder.append("</xml>");
            return strBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String mapToUrlForWeixin(Map<String, String> params, boolean needSort)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        if (needSort)
            Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String value = params.get(keys.get(i));
            if (i == 0) {
                sb.append(keys.get(i) + "=" + value);
            } else {
                if (value != null) {
                    sb.append("&" + keys.get(i) + "="
                            + value);
                } else {

                }
            }
        }
        return sb.toString();
    }

    /**
     * 非常简单的解析，只要忽略xml中的CDATA就ok，没有子结构<xml></xml>
     * 忽略了空的<><>的情况，因为生成url的时候并不想再写一个函数，所以在这里解决空的情况
     * 大概就用来简单的获取正常返回的xml吧，第三方如果发错的东西给我，我只能通过后续校验了 不保证异常情况能正常处理
     * 流的形式发送过来也用不着解码
     *
     * @param xmlStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> xmlToMap(String xmlStr) {
        try {
            if (StringUtils.isEmpty(xmlStr)) {
                return null;
            }
//			xmlStr = URLDecoder.decode(xmlStr, "utf-8");
            Map<String, String> params = new HashMap<String, String>();
            StringBuffer key = new StringBuffer(50);
            StringBuffer value = new StringBuffer(100);
            char[] xmlChars = xmlStr.toCharArray();
            for (int i = 0; i < xmlChars.length; i++) {
                key.delete(0, key.length());
                value.delete(0, value.length());
                while (xmlChars[i] != '<') {
                    i++;
                }
                if (xmlChars[i] == '<') {
                    for (; i < xmlChars.length; ) {
                        i++;
                        if (xmlChars[i] == '<' && xmlChars[i + 1] == '!') {
                            i += 9;
                            break;
                        }
                        if (xmlChars[i] == '/') {
                            return params;
                        }
                        if (xmlChars[i] == '>') {
                            i++;
                            break;
                        }
                        key.append(xmlChars[i]);
                    }
                }
                if (StringUtils.isEmpty(key.toString()) || StringUtils.equals(key.toString(), "xml")) {
                    //因为continue会i++
                    i--;
                    continue;
                }
                if (xmlChars[i] == '<' && xmlChars[i + 1] == '!') {
                    i += 9;
                }
                for (; i < xmlChars.length; i++) {
                    if (xmlChars[i] == ']') {
                        i += 4;
                        break;
                    }
                    if (xmlChars[i] == '<') {
                        i++;
                        break;
                    }
                    value.append(xmlChars[i]);
                }
                if (StringUtils.isEmpty(value.toString())) {
                    i--;
                    continue;
                }
                params.put(key.toString(), value.toString());
            }
            return params;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> urlEncode(Map<String, String> param) {
        Map<String, String> p = new HashMap<String, String>();
        try {
            if (param == null || param.isEmpty()) {
                return p;
            }
            for (String key : param.keySet()) {
                p.put(key, encodeURIComponent(param.get(key)));
            }
            return p;
        } catch (Exception e) {
            return p;
        }
    }

    public static String encodeURIComponent(String component) {
        String result = null;
        try {
            result = URLEncoder.encode(component, "UTF-8")
                    .replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                    .replaceAll("\\+", "%20").replaceAll("\\%27", "'")
                    .replaceAll("\\%21", "!").replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = component;
        }
        return result;
    }
}
