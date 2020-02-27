package com.ggf.api.testcase;

import com.ggf.api.config.Constant;
import com.ggf.api.pojo.ApiInfo;
import com.ggf.api.pojo.CaseInfo;
import com.ggf.api.utils.ExcelUtils;
import com.ggf.api.utils.HttpUtils;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Description: 注册用例
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class RegisterCase extends BaseCase{
    /**
     * 日志类
     */
    private static Logger log = Logger.getLogger(RegisterCase.class);

    @Test(dataProvider = "regDatas", description = "注册用例")
    public void testRegister(ApiInfo api, CaseInfo caseInfo) {
        // 1.发送请求
        log.info("发送注册请求----->");
        String body = HttpUtils.request(api, caseInfo, false);
        // 2.断言
        boolean respFlag = assertResponse(caseInfo.getExpectValue(), body);
        log.info("期望值："+ caseInfo.getExpectValue() + " 结果值：" + body + "断言结果：" + respFlag);
        // 3.测试结果
        String result = respFlag ? "Pass" : "Fail";
        // 4.结果回写
        int row = Integer.parseInt(caseInfo.getId());
        // 响应结果值
        saveWriteBackData(row, Constant.WRITE_BACK_CELL_RESP_NUM, body);
        // 测试结果
        saveWriteBackData(row, Constant.WRITE_BACK_CELL_RESULT_NUM, result);
        log.info("注册用例结束--->");
    }

    /**
     * 数据源，获取接口数据和用例数据
     * @return
     */
    @DataProvider(name = "regDatas")
    @Step("加载测试数据！")
    public Object[][] datas() {
        return ExcelUtils.getApiAndCase(Constant.REGISTER_API_ID, ExcelUtils.apiList, ExcelUtils.caseList);
    }
}
