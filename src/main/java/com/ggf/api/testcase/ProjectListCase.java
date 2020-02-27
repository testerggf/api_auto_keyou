package com.ggf.api.testcase;

import com.ggf.api.config.Constant;
import com.ggf.api.pojo.ApiInfo;
import com.ggf.api.pojo.CaseInfo;
import com.ggf.api.utils.ExcelUtils;
import com.ggf.api.utils.HttpUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Description: 获取项目列表用例
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class ProjectListCase extends BaseCase {
    public static Logger log = Logger.getLogger(ProjectListCase.class);

    @Test(dataProvider = "plDatas", description = "获取项目列表测试用例！")
    public void testProjectList(ApiInfo apiInfo, CaseInfo caseInfo) {
        //1.发送获取项目列表请求
        log.info("发送请求-->");
        String body = HttpUtils.request(apiInfo, caseInfo, true);
        // 2.对响应结果断言
        log.info("响应内容断言-->");
        boolean respFlag = assertResponse(caseInfo.getExpectValue(), body);
        log.info("期望值："+ caseInfo.getExpectValue() + " 结果值：" + body + "断言结果：" + respFlag);
        // 测试结果
        String result = respFlag ? "Pass" : "Fail";
        // 3.回写数据
        int row = Integer.parseInt(caseInfo.getId());
        saveWriteBackData(row, Constant.WRITE_BACK_CELL_RESP_NUM, body);
        saveWriteBackData(row, Constant.WRITE_BACK_CELL_RESULT_NUM, result);
        log.info("获取项目列表用例结束-->");
    }

    @DataProvider(name = "plDatas")
    public Object[][] datas() {
        return ExcelUtils.getApiAndCase(Constant.GET_PROJECTS_API_ID, ExcelUtils.apiList, ExcelUtils.caseList);
    }

}
