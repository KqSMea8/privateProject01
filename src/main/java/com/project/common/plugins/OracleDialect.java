package com.project.common.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleDialect extends Dialect {
	
	static final Logger log = LoggerFactory.getLogger(OracleDialect.class);

	@Override
	public String getLimitString(String sql, int skipResults, int maxResults) {
		sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        pagingSelect.append(sql);
        pagingSelect.append(" ) row_ ) where rownum_ > ").append(skipResults).append(" and rownum_ <= ").append(skipResults + maxResults);
        //System.out.println("分页SQL=="+pagingSelect.toString());
        return pagingSelect.toString();
	}

}
