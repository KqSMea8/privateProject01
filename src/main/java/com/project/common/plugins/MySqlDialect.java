package com.project.common.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySqlDialect extends Dialect {
	
	static final Logger log = LoggerFactory.getLogger(MySqlDialect.class);

	@Override
	public String getLimitString(String sql, int skipResults, int maxResults) {
		sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append(sql);
        pagingSelect.append(" limit ").append(skipResults).append(" , ").append(maxResults);
        
        return pagingSelect.toString();
	}
}
