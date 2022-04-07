package com.menu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @describe
 * @date 2020/
 */
@ApiModel(value = "分页对象")
@Slf4j
public class CustomPageDto<T> implements Serializable {
    @ApiModelProperty(value = "起始索引")
    private Integer start;
    @ApiModelProperty(value = "当前页")
    private Integer currentPage=1;
    @ApiModelProperty(value = "每页显示的条数")
    private Integer pageSize=10;
    @ApiModelProperty(value = "查询条件")
    private Map query=new HashMap();
    @ApiModelProperty(value = "总条数")
    private Integer pageCount;
    @ApiModelProperty(value = "数据集")
    private T data;
    public CustomPageDto(int pageCount, T data){
        this.pageCount=pageCount;
        this.data=data;
    }

    public CustomPageDto(){};

    public Integer getStart() {
        return (this.currentPage-1)*pageSize;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public Map getQuery() {
        return query;
    }

    public void setQuery(Map query) {
        this.query = query;
    }
}
