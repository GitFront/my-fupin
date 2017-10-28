package com.aspire.birp.modules.smartQuery.exception.entity;

/**
 * 异常里面的模块类型
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/4 9:11
 */
public enum ModuleType {
    SQ_TASK(1,"自动任务");

    /**
     * 模块的Id
     */
    private Integer id;

    /**
     * 模块名称
     */
    private String name;

    private ModuleType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
