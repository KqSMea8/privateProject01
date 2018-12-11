package com.project.common.plugins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceRequest {
	
	static final Logger log = LoggerFactory.getLogger(DataSourceRequest.class);
	
	/** 当前页码 **/
    private int pageIndex;
    /** 每页显示记录数 **/
    private int pageSize;
    
    /** 排序集合 **/
    SortDescriptor sort;
    
    /** mini pagerTree 合并节点属性  **/
    Map<String, List<Object>>  __ecconfig;

    /** 自定义 查询条件集合 **/
    private HashMap<String, Object> data;
    
    private String totalSql;
	//总记录数
    private long total; 

	public DataSourceRequest() {
		data = new HashMap<String, Object>();
		sort = new SortDescriptor();
    }
 
    public HashMap<String, Object> getData() {
        return data;
    }
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public SortDescriptor getSort() {
		return sort;
	}

	public void setSort(SortDescriptor sort) {
		this.sort = sort;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
	
	public String getTotalSql() {
		return totalSql;
	}

	public void setTotalSql(String totalSql) {
		this.totalSql = totalSql;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Map<String, List<Object>> get__ecconfig() {
		return __ecconfig;
	}

	public void set__ecconfig(Map<String, List<Object>> __ecconfig) {
		this.__ecconfig = __ecconfig;
	}
	

	//排序
	public static class SortDescriptor {
		
        private String sortField;
        private String sortOrder;
        
		public String getSortField() {
			return sortField;
		}
		public void setSortField(String sortField) {
			this.sortField = sortField;
		}
		public String getSortOrder() {
			return sortOrder;
		}
		public void setSortOrder(String sortOrder) {
			this.sortOrder = sortOrder;
		}
		
		@Override
		public String toString() {
			return "SortDescriptor [sortField=" + sortField + ", sortOrder="
					+ sortOrder + "]";
		}
    }

	@Override
	public String toString() {
		return "DataSourceRequest [pageIndex=" + pageIndex + ", pageSize="
				+ pageSize + ", sort=" + sort + ", data=" + data
				+ ", totalSql=" + totalSql + ", total=" + total + "]";
	}
	
}
