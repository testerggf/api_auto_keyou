package com.ggf.api.pojo;

/**
 * @Description: 封装jsonpath匹配数据
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class JsonPathValidate {
    /**
     * jsonpath表达式
     */
    private String expression;

    /**
     * 期望值
     */
    private String value;

    public JsonPathValidate() {
    }

    public JsonPathValidate(String expression, String value) {
        this.expression = expression;
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "JsonPathValidate{" +
                "expression='" + expression + '\'' +
                ", values='" + value + '\'' +
                '}';
    }
}
