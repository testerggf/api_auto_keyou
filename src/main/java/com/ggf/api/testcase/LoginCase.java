package com.ggf.api.testcase;

import com.ggf.api.config.Constant;
import com.ggf.api.pojo.ApiInfo;
import com.ggf.api.pojo.CaseInfo;
import com.ggf.api.utils.EnvUtils;
import com.ggf.api.utils.ExcelUtils;
import com.ggf.api.utils.HttpUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Description: 登录用例
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class LoginCase extends BaseCase{
    public static Logger log = Logger.getLogger(LoginCase.class);

    @Test(dataProvider = "loginDatas", description = "登录用例！")
    public void testLogin(ApiInfo apiInfo, CaseInfo caseInfo) {
        // 1.发送登录请求
        log.info("发送登录请求--->");
        String body = HttpUtils.request(apiInfo, caseInfo, false);
        // 2.断言
        log.info("对响应内容断言--->");
        boolean respFlag = assertResponse(caseInfo.getExpectValue(), body);
        log.info("期望值："+ caseInfo.getExpectValue() + " 结果值：" + body + "断言结果：" + respFlag);
        // 测试结果
        String resutl = respFlag ? "Pass" : "Fail";
        // 3.保存token信息
        log.info("保存token信息--->");
        EnvUtils.storeToken(body);
        log.info("保存回写内容--->");
        int row = Integer.parseInt(caseInfo.getId());
        saveWriteBackData(row, Constant.WRITE_BACK_CELL_RESP_NUM, body);
        saveWriteBackData(row, Constant.WRITE_BACK_CELL_RESULT_NUM, resutl);
        log.info("登录用例结束--->");
    }

    @DataProvider(name = "loginDatas")
    public Object[][] datas() {
        return ExcelUtils.getApiAndCase(Constant.LOGIN_API_ID, ExcelUtils.apiList, ExcelUtils.caseList);
    }
}
