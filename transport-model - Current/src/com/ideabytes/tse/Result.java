package com.ideabytes.tse;

public class Result {
    private String description;
    private Object value;

    public Result() {
    }
    /**
     * Constructor
     * @param description what the method does
     * @param value value of return
     */
    public Result(String description, Object value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
