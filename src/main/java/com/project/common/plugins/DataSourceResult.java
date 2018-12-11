package com.project.common.plugins;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceResult {
	
	static final Logger log = LoggerFactory.getLogger(DataSourceResult.class);
	
	/** 总记录数 **/
    private long total;
    /** 查询分页数据 **/
    private List<?> data;
    /** 聚合数据 **/
    private Map<String, Object> aggregates;
    /** 提示信息 **/
    private String message;
    /** 扩展数据 **/
    private Map<String, Object> edata;

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Map<String, Object> getAggregates() {
        return aggregates;
    }

    public void setAggregates(Map<String, Object> aggregates) {
        this.aggregates = aggregates;
    }

	public Map<String, Object> getEdata() {
		return edata;
	}

	public void setEdata(Map<String, Object> edata) {
		this.edata = edata;
	}
    
}
