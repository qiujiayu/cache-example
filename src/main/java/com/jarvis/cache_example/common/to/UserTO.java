package com.jarvis.cache_example.common.to;

import java.io.Serializable;

public class UserTO implements Serializable {

    private static final long serialVersionUID=-2510806368101425770L;

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

}
