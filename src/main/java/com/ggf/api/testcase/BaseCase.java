package com.ggf.api.testcase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.ggf.api.pojo.JsonPathValidate;
import com.ggf.api.pojo.WriteBack;
import com.ggf.api.utils.ExcelUtils;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;

import java.util.List;

/**
 * @Description: 测试用例基类，封装公用的方法
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class BaseCase {
    public static Logger log = Logger.getLogger(BaseCase.class);

    /**
     * 通用的回写数据方法，调用该方法将需要回写的数据存储到全局变量中
     * ExcelUtils.wbList
     * @param row 回写的行
     * @param cell 回写的列
     * @param content 回写的内容
     */
    @Step("保存回写数据！")
    protected void saveWriteBackData(int row, int cell, String content) {
        // 创建对象
        WriteBack wb = new WriteBack(row, cell, content);
        // 将对象添加到回写数据集合中
        ExcelUtils.wbList.add(wb);
    }

    /**
     * 断言响应结果
     * @param expectValue 期望值
     * @param body 响应结果
     */
    @Step("断言请求响应结果！")
    protected boolean assertResponse(String expectValue, String body) {
        // 定义一个响应结果标志
        boolean flag = false;
        /*由于期望值是一个json串，包含表达式和期望值，将json转为对象（jsonpathvalidate）
         也可能是一个纯json串不包含表达式
         使用关键字 instanceof 来判断类型，不同类型使用不同的比较方法
         */

        // 将响应结果转换为json对象
        Object jsonObj = JSONObject.parse(expectValue);
        // 判断类型
        if (jsonObj instanceof JSONArray) {
            List<JsonPathValidate> jsonPathValidates = JSONObject.parseArray(expectValue, JsonPathValidate.class);
            // 遍历该集合进行断言
            for (JsonPathValidate jsonPathValidate : jsonPathValidates) {
                // 获取表达式
                String expression = jsonPathValidate.getExpression();
                // 获取期望值
                String expect = jsonPathValidate.getValue();
                // 实际值
                String actual = JSONPath.read(body, expression) == null ? ""
                        : JSONPath.read(body, expression).toString();
                // 对期望值和实际值进行判断
                flag = expect.equals(actual);
                log.info("期望值：" + expect + ",实际结果：" + actual);
                // 如果有一个值不匹配就退出循环
                if (!flag) {
                    break;
                }

            }
        }else if (jsonObj instanceof JSONObject) {
            flag = body.equals(expectValue);
        }

        return flag;
    }

    /**
     * 用例执行完之后，执行回写数据方法，批量进行回写
     */
    @AfterSuite
    @Step("用例结束执行方法！")
    public void finish() {
        // 等所有用例执行后执行该方法
        ExcelUtils.write();
    }

}
