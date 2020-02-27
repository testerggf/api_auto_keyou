package com.ggf.api.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.ggf.api.config.Constant;
import com.ggf.api.pojo.ApiInfo;
import com.ggf.api.pojo.CaseInfo;
import com.ggf.api.pojo.WriteBack;
import io.qameta.allure.Step;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 操作Excel的工具类
 * 使用easypoi对应Excel的读写操作
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class ExcelUtils {

    /**
     * 读取Excel中接口和用例的数据，随着类加载而加载
     * 使用静态的数据，全局共享，只需要加载一次即可。
     */
    public static List<ApiInfo> apiList =
            ExcelUtils.read(Constant.EXCEL_PATH, Constant.API_SHEET_INDEX, ApiInfo.class);

    public static List<CaseInfo> caseList =
            ExcelUtils.read(Constant.EXCEL_PATH, Constant.CASE_SHEET_INDEX, CaseInfo.class);

    /**
     * 回写数据集合，全局共享，批量回写的就是这个集合的数据
     */
    public static List<WriteBack> wbList = new ArrayList<WriteBack>();


    /**
     * 使用easypoi来读对应的Excel数据
     * @param filePath 文件路径
     * @param sheetIndex 工作表坐标（从0开始）
     * @param clazz 对应的字节码对象，用来映射数据
     * @return 返回一个集合
     */
    public static <E> List<E> read(String filePath, int sheetIndex, Class<E> clazz) {
        // 定义输入流
        FileInputStream fis = null;
        List<E> datas = null;

        try {
            // 创建输入流对象
            fis = new FileInputStream(filePath);
            // 创建一个easypoi使用的配置类
            ImportParams params = new ImportParams();
            // 设置表格坐标
            params.setStartSheetIndex(sheetIndex);
            // 校验Excel文件，去掉空行
            params.setNeedVerify(true);
            // 读取数据
            datas = ExcelImportUtil.importExcel(fis, clazz, params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fis);
        }

        return datas;
    }

    /**
     * 写方法，用来将数据回写到Excel中
     */
    public static void write() {
        // 定义流属性
        FileInputStream fis = null;
        FileOutputStream fos = null;
        // 定义工作簿
        Workbook workbook = null;

        try {
            // 创建对象
            fis = new FileInputStream(Constant.EXCEL_PATH);
            workbook = WorkbookFactory.create(fis);
            // 创建sheet对象
            Sheet sheet = workbook.getSheetAt(Constant.CASE_SHEET_INDEX);

            // 定义行和列对象
            Row row = null;
            Cell cell = null;

            // 遍历回写数据集合
            for (WriteBack wb : wbList) {
                // 获取行号和列号
                row = sheet.getRow(wb.getRowNum());
                // 减 1 是因为坐标从0开始
                cell = row.getCell(wb.getCellNum()-1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                // 设置列
                cell.setCellType(CellType.STRING);
                cell.setCellValue(wb.getContent());
            }

            // 回写
            workbook.write(new FileOutputStream(Constant.EXCEL_PATH));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(fis);
            close(workbook);
            close(fos);
        }



    }

    /**
     * 根据接口Id获取 API和Case的关系数据。
     * @param apiId 接口ID
     * @param apiList api对象集合
     * @param caseList case对象集合
     * @return 返回api对应的用例数据
     * 如：注册接口ID为：1
     * 对应的用例数据有6条。
     * 那么对应的二维数组结构为：Object[][] datas = {{API,Case},{Api, Case}, ....}
     */
    @Step("获取用例数据")
    public static Object[][] getApiAndCase(String apiId, List<ApiInfo> apiList, List<CaseInfo> caseList) {
        //1、获取wantAPI
        ApiInfo wantApi = null;
        for (ApiInfo apiInfo : apiList) {
            // 如果传入来的id和集合中api对象的id一致，就是我们要的接口对象
            if (apiId.equals(apiInfo.getId())) {
                wantApi = apiInfo;
                break;
            }
        }

        //2、获取wantCases,定义一个case集合，用来获取传入接口id，对应的用例
        List<CaseInfo> wantCases = new ArrayList<CaseInfo>();
        for (CaseInfo caseInfo : caseList) {
            try {
                // 根据传入接口id和用例集合中的接口id比对，一致就是我们要的用例
                if (wantApi.getId().equals(caseInfo.getApiId())) {
                    wantCases.add(caseInfo);
                }
            }catch (Exception e) {
                throw new RuntimeException("没有找到对应的接口对象，请检查数据是否正确！", e);
            }
        }

        //3、把wantAPI和wantCases合并，存入到二维数组中。
        // 定义一个二维数组，行为用例的长度，列固定为2，一个是接口对象，一个是用例对象
        Object[][] datas = new Object[wantCases.size()][2];
        // 遍历数组将对应的数据存储进去
        for (int i = 0; i < datas.length; i++) {
            // 接口对象数据
            datas[i][0] = wantApi;
            // 用例对象数据
            datas[i][1] = wantCases.get(i);
        }

        // 将数据返回
        return datas;
    }

    /**
     * 自定义关闭资源方法
     * @param stream
     */
    private static void close(Closeable stream) {

        try {
            if(stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
