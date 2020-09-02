package com.ideabytes.tse;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*******************************************************************
 * Ideabytes Inc.
 * Luka Macieszczak
 * July 28th, 2020
 * Purpose: Creates Parameter objects which get stored in DB and JSON object
 ********************************************************************/
public class Parameter {
    private String name;
    @JsonIgnore
    private Object value;

    public Parameter() {
    }

    /**
     * Constructor
     * @param name name of parameter
     * @param value value of parameter
     */
    public Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
