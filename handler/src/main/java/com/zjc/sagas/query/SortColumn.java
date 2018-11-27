package com.zjc.sagas.query;

import java.io.Serializable;

/**
* 排序对象
* Created by AutoGenerate on 18-11-26 下午2:54 .
*/
public class SortColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    // 排序字段
    private String columnName;

    // 排序:asc或desc
    private SortMode sortMode;

    public SortColumn(String columnName, SortMode sortMode) {
        this.columnName = columnName;
        this.sortMode = sortMode;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public SortMode getSortMode() {
        return sortMode;
    }

}
