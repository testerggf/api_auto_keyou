package com.ggf.api.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;


/**
 * @Description: 接口信息类
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class ApiInfo {
    /**
     * 接口编号
     */
    @Excel(name="接口编号")
    private String id;
    /**
     * 接口名称
     */
    @Excel(name="接口名称")
    private String name;
    /**
     * 接口请求方式
     */
    @Excel(name="提交方式")
    private String method;
    /**
     * 请求URL
     */
    @Excel(name="请求地址")
    @NotNull
    private String url;
    /**
     * 请求数据类型
     */
    @Excel(name="参数类型")
    private String type;

    public ApiInfo() {
    }

    public ApiInfo(String id, String name, String method, String url, String type) {
        this.id = id;
        this.name = name;
        this.method = method;
        this.url = url;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
