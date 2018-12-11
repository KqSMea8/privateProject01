package com.project.common.plugins;

public abstract class Dialect {

    public abstract String getLimitString(String sql, int skipResults, int maxResults);
    
}
