package com.wenna4.ppp.Intf.utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("deprecation")
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    @SuppressWarnings("rawtypes")
    public static Map convert(String jsonText) {
        if (jsonText == null || jsonText.length() == 0) return new LinkedHashMap();
        try {
            return mapper.readValue(jsonText, Map.class);
        } catch (JsonParseException e) {
            return null;
        } catch (JsonMappingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static Map<String, String> convert2Map(String jsonText) {

        if (jsonText == null || jsonText.length() == 0) return new LinkedHashMap<String, String>();

        try {
            return mapper.readValue(jsonText, new TypeReference<Map<String, String>>() {
            });
        } catch (JsonParseException e) {
            return null;
        } catch (JsonMappingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 将对象转换为JSON格式
     *
     * @param model
     * @return
     * @throws IOException
     */
    public static String toStr(Object model) {
        if (model == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(model);
        } catch (Exception ex) {
            logger.error("JsonParseException=" + ex + ",jsonText=" + model);
            return "";
        }
    }

    public static Object convert(String jsonText, Class<?> valueType) {
        if (jsonText == null || jsonText.length() == 0)
            return null;

        try {
            return mapper.readValue(jsonText, valueType);
        } catch (JsonParseException e) {
            logger.error("JsonParseException=" + e + ",jsonText=" + jsonText);
            return null;
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException=" + e + ",jsonText=" + jsonText);
            return null;
        } catch (IOException e) {
            logger.error("IOException=" + e + ",jsonText=" + jsonText);
            return null;
        }
    }

    public static Object convert(File src, Class<?> valueType) {
        if (src == null || src.length() == 0)
            return null;

        try {
            return mapper.readValue(src, valueType);
        } catch (JsonParseException e) {
            logger.error("JsonParseException=" + e);
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException=" + e);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            logger.error("IOException=" + e);
            e.printStackTrace();
            return null;
        }
    }

    public static String convert(Map map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonGenerationException e) {
            return null;
        } catch (JsonMappingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(String jsonText, Class<T> valueType) {
        if (jsonText == null || jsonText.length() == 0)
            return null;

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, valueType);

            //这两句：排除JSON中含有 Bean所没有的属性 的影响
//			ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
//			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return (List<T>) mapper.readValue(jsonText, javaType);
        } catch (IOException e) {
            logger.error("IOException=" + e + ",jsonText=" + jsonText);
            return null;
        }
    }

}
