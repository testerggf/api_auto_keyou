package com.ggf.api.config;

/**
 * @Description: 常量内容类，用来存储配置信息
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class Constant {
    /**
     * Excel文件路径
     */
    public static final String EXCEL_PATH = "src/main/resources/keyou.xlsx";

    /**
     * 接口表单下标
     */
    public static final int API_SHEET_INDEX = 0;
    /**
     * 用例表单下标
     */
    public static final int CASE_SHEET_INDEX = 1;

    /**
     * Excel中实际响应数据的列，坐标为6
     */
    public static final int WRITE_BACK_CELL_RESP_NUM = 6;

    /**
     * Excel中是否验证通过的列，坐标为7
     */
    public static final int WRITE_BACK_CELL_RESULT_NUM = 7;


    /**
     * 注册接口id
     */
    public static final String REGISTER_API_ID = "1";
    /**
     * 登录接口id
     */
    public static final String LOGIN_API_ID = "2";
    /**
     * 获取配置信息接口id
     */
    public static final String CONFIGURES_API_ID = "3";
    /**
     * 获取项目列表接口id
     */
    public static final String GET_PROJECTS_API_ID = "4";
    /**
     * 获取项目列表接口id
     */
    public static final String CREATE_PROJECTS_API_ID = "5";



}
