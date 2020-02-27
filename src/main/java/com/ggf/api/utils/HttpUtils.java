package com.ggf.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.ggf.api.pojo.ApiInfo;
import com.ggf.api.pojo.CaseInfo;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: http请求工具类 包括了get、post、patch
 * @Author: ggf
 * @Date: 2020/02/08
 */
public class HttpUtils {
    public static Logger log = Logger.getLogger(HttpUtils.class);
    /**
     * 定义一个客户端属性
     */
    private static HttpClient client = HttpClients.createDefault();


    /**
     * 定义一个通用的请求方法
     * @param apiInfo 接口内容实体类
     * @param caseInfo 用例内容实体类
     * @param isAuthorization 是否需要认证
     * @return 返回响应体
     */
    @Step("发送请求！")
    public static String request(ApiInfo apiInfo, CaseInfo caseInfo, boolean isAuthorization) {
        // 获取需求的参数
        // 请求方法
        String type = apiInfo.getMethod();
        // 请求URL
        String url = apiInfo.getUrl();
        // 请求内容类型
        String contentType = apiInfo.getType();
        // 请求参数
        String params = caseInfo.getParams();

        log.info("请求方法：【"+ type + "】");
        log.info("请求URL：【" + url + "】");
        log.info("请求数据类型：【" + contentType + "】");
        log.info("请求参数：【" + params + "】");
        log.info("期望结果：【" + caseInfo.getExpectValue() + "】");

        // 组装请求头信息，用map来存储头信息
        HashMap<String,String> headers = new HashMap<String, String>();
        // 判断一下头类型，根据不同的类型组装不同的头信息
        if ("form".equalsIgnoreCase(contentType)) {
            headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        }else if ("json".equalsIgnoreCase(contentType)) {
            headers.put("Content-Type", "application/json;charset=utf-8");
        }

        // 判断 isAuthorization 是否需要鉴权，如果为 true时，就加上token信息
        if (isAuthorization) {
            // 组装token头数据
            String token = EnvUtils.ENV.get("token");
            if (StringUtils.isNotEmpty(token)) {
                headers.put("Authorization", "JWT " + token);
            }
        }

        // 处理请求参数,如果是form格式的请求，要保证参数为key=value&key=value
        if ("form".equalsIgnoreCase(contentType)) {
            params = json2KeyValue(params);
        }

        // 处理普通的get请求,直接将参数拼接到URL上
        if ("none".equalsIgnoreCase(contentType)) {
            if (StringUtils.isNotEmpty(params)) {
                url = url + params;
            }
        }


        // 定义返回值
        String body = "";
        // 判断请求方式,根据不同的请求方式调用不同的方法。
        if ("get".equalsIgnoreCase(type)) {
            body = HttpUtils.doGet(url, headers);

        }else if ("post".equalsIgnoreCase(type)) {
            body = HttpUtils.doPost(url, params, headers);

        }else if ("patch".equalsIgnoreCase(type)) {
            body = HttpUtils.doPatch(url, params, headers);
        }

        return body;

    }



    /**
     * 封装get请求方法
     * @param url 请求的URL
     * @param headers 请求头信息
     */
    public static String doGet(String url, Map<String, String> headers) {
        // 创建get对象
        HttpGet get = new HttpGet(url);
        // 设置头信息
        for (String key : headers.keySet()) {
            get.addHeader(key, headers.get(key));
        }
        // 发送请求，处理响应信息
        try {
            HttpResponse response = client.execute(get);
            // 获取响应体信息,设置编码
            String body = EntityUtils.toString(response.getEntity(),"utf-8");
            // 输出响应信息
            log.info("响应状态：" + response.getStatusLine().getStatusCode());
            log.info("响应结果：" + body);

            return body;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 封装post请求
     * @param url 请求URL
     * @param params 请求参数
     * @param headers 请求头
     */
    public static String doPost(String url, String params, Map<String, String> headers) {
        // 创建post对象
        HttpPost post = new HttpPost(url);

        // 设置头信息
        for (String key : headers.keySet()) {
            post.addHeader(key, headers.get(key));
        }

        try {
            // 设置请求体信息
            post.setEntity(new StringEntity(params,"utf-8"));

            // 发送请求，处理响应信息
            HttpResponse response = client.execute(post);
            // 响应体信息
            String body = EntityUtils.toString(response.getEntity(), "utf-8");
            // 输出响应信息
            log.info("响应状态：" + response.getStatusLine().getStatusCode());
            log.info("响应结果：" + body);

            return body;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 封装patch请求
     * @param url 请求URL
     * @param params 请求参数
     * @param headers 请求头信息
     */
    public static String doPatch(String url, String params, Map<String, String> headers) {
        // 创建post对象
        HttpPatch post = new HttpPatch(url);

        // 设置头信息
        for (String key : headers.keySet()) {
            post.addHeader(key, headers.get(key));
        }
        try {
            // 设置请求体信息
            // 注意这里设置请求体时，需要设置一下编码，因为httpclient默认编码是：ISO-8859-1
            // 如果在修改内容时，不设置对应的编码，入库时会产生乱码。（踩坑记录）
            post.setEntity(new StringEntity(params, "utf-8"));

            // 发送请求，处理响应信息
            HttpResponse response = client.execute(post);
            // 响应体信息
            String body = EntityUtils.toString(response.getEntity(), "utf-8");
            // 输出响应信息
            log.info("响应状态：" + response.getStatusLine().getStatusCode());
            log.info("响应结果：" + body);

            return body;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将json参数转换为key=value&key=value格式返回
     * @param json json数据
     * @return 返回key=value&key=value格式数据
     */
    public static String json2KeyValue(String json) {
        // 将json数据解析为map
        HashMap<String, String> jsonMap = JSONObject.parseObject(json, HashMap.class);

        // 存储转换后的结果
        String result = "";

        // 遍历map
        for (String key : jsonMap.keySet()) {
            if (result.length() != 0) {
                result += "&" + key + "=" + jsonMap.get(key);
            }else {
                result += key + "=" + jsonMap.get(key);
            }
        }

        return result;
    }
}
