package com.ggf.api.utils;

import com.alibaba.fastjson.JSONPath;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 存储环境变量的工具类
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class EnvUtils {
    public static Logger log = Logger.getLogger(EnvUtils.class);
    /**
     * 用来存储环境变量信息
     */
    public static final Map<String, String> ENV = new HashMap<String, String>();

    /**
     * 从响应体中获取token信息，将信息存储到全局变量 ENV中。
     * @param response 响应体信息
     */
    @Step("存储token信息！")
    public static void storeToken(String response) {
        // 使用jsonpath技术获取token
        String token = (String) JSONPath.read(response, "$.token");

        // 判断获取的token是否为空，不为空继续获取memberid,然后将获取到的值添加到集合中
        if (StringUtils.isNotEmpty(token)) {
            // 添加到集中
            log.info("token的值：" + token);
            ENV.put("token", token);
        }
    }
}
