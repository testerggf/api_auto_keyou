package com.ggf.api.pojo;

/**
 * @Description: 回写数据量类
 * 包含行号，列号和回写数据
 * @Author: ggf
 * @Date: 2020/02/27
 */
public class WriteBack {
    /**
     * 回写行号
     */
    private int rowNum;
    /**
     * 回写列号
     */
    private int cellNum;
    /**
     * 回写数据
     */
    private String content;

    public WriteBack() {
    }

    public WriteBack(int rowNum, int cellNum, String content) {
        this.rowNum = rowNum;
        this.cellNum = cellNum;
        this.content = content;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WriteBack{" +
                "rowNum=" + rowNum +
                ", cellNum=" + cellNum +
                ", content='" + content + '\'' +
                '}';
    }
}
