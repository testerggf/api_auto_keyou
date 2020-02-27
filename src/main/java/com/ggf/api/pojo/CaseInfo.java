package com.ggf.api.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;

/**
 * @Description: 用例类
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class CaseInfo {
    /**
     * 用例编号
     */
    @Excel(name="用例编号")
    private String id;
    /**
     * 用例描述
     */
    @Excel(name="用例描述")
    private String description;
    /**
     * 请求参数
     */
    @Excel(name="参数")
    private String params;
    /**
     * 接口编号
     */
    @Excel(name="接口编号")
    @NotNull
    private String apiId;

    /**
     * 期望结果值
     */
    @Excel(name = "期望响应数据")
    private String expectValue;

    public CaseInfo() {
    }

    public CaseInfo(String id, String description, String params, String apiId, String expectValue) {
        this.id = id;
        this.description = description;
        this.params = params;
        this.apiId = apiId;
        this.expectValue = expectValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getExpectValue() {
        return expectValue;
    }

    public void setExpectValue(String expectValue) {
        this.expectValue = expectValue;
    }

    @Override
    public String toString() {
        return "CaseInfo{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", params='" + params + '\'' +
                ", apiId='" + apiId + '\'' +
                ", expectValue='" + expectValue + '\'' +
                '}';
    }
}
