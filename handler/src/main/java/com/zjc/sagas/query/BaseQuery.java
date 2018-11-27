package com.zjc.sagas.query;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
* 基本查询对象
* Created by autoGenerate on 18-11-26 下午2:54 .
*/
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 最大条数
    */
    private static final int MAX_ROWS = 5000;

    /**
    * 排序
    */
    private List<SortColumn> sorts;

    /**
    * 记录开始位置
    */
    private Integer offset;

    /**
    * 记录行数
    */
    private Integer rows;

    public BaseQuery() {
    }

    public BaseQuery(int pageNo, int pageSize) {
        this.offset = (pageNo - 1) * pageSize;
        this.rows = pageSize;
    }

    public List<SortColumn> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortColumn> sorts) {
        this.sorts = sorts;
    }

    public void setSorts(SortColumn sort) {
        if (null == sort) {
            return;
        }
        this.sorts = Arrays.asList(sort);
    }

    public Integer getOffset() {
        return offset;
    }

    public BaseQuery setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getRows() {
        return rows;
    }

    public BaseQuery setRows(Integer rows) {
        this.rows = rows;
        return this;
    }


    public void checkBaseQuery() {

        if (null == this.getOffset()) {
            throw new IllegalArgumentException("查询偏移量不能为空");
        }

        if (null == this.getRows()) {
            throw new IllegalArgumentException("查询记录数量不能为空");
        }

        if (this.getOffset() < 0) {
            throw new IllegalArgumentException("查询偏移量必须大于等于0");
        }

        if (this.getRows() <= 0) {
            throw new IllegalArgumentException("查询记录数量必须大于0");
        }

        if (this.getRows() > MAX_ROWS) {
            throw new IllegalArgumentException("查询记录数量不能超过" + MAX_ROWS);
        }

    }


}
