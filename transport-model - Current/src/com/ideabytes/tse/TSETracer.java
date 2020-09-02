package com.ideabytes.tse;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*******************************************************************
 * Ideabytes Inc.
 * Luka Macieszczak
 * July 28th, 2020
 * Purpose: Takes information gathered by TSEHandler and puts it into database
********************************************************************/
public class TSETracer {
    @JsonManagedReference
    private long eventTime;
    private String className = null;
    private String methodName = null;
    private List<Parameter> params = null;
    private com.ideabytes.tse.Result result = null;
    //private Action action = null;
    public TSETracer() {
    }

    public long getEventTime() {
        return eventTime;
    }

    public List<Parameter> getParams() {
        return params;
    }



    /**
     * Constructor
     * @param className name of class
     * @param methodName name of method
     * @param inputParamNames parameters being inputted
     */
    public TSETracer(String className, String methodName , List<Parameter> inputParamNames) {
        this.eventTime = System.currentTimeMillis();
        this.className = className;
        this.params = inputParamNames;
        this.methodName = methodName;

    }


    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public com.ideabytes.tse.Result getResult() {
        return result;
    }

    public void setResult(com.ideabytes.tse.Result result) {
        this.result = result;
    }

    /**
     * Adds to database
     * @param statement SQL statement
     * @param appName name of app
     * @throws JsonProcessingException
     */
    void addToDB (Statement statement, String appName, boolean verbose ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String paramMapAsJson = "void";
        String resultString = "void";
        Map<String, Object> parametersMap = new HashMap<>();
        if (params != null && !params.isEmpty()) {
            for (Parameter p : params ) {
                 parametersMap.put(p.getName(), p.getValue().toString());
            }
            paramMapAsJson = mapper.writeValueAsString(parametersMap);
        }
        if (result!=null){
            resultString = mapper.writeValueAsString(result);
        }


        String insert = "INSERT INTO TSE3 (appName , startTime, className, methodName, result, inputParams) " +
                "VALUES ("+"\""+appName+"\""+" ,"+this.eventTime + ",\"" + this.className + "\",\"" + this.methodName+"\",\" "+addQuotes(resultString) + "\",\"" +addQuotes(paramMapAsJson) + "\""  +")";
        try {

            if (verbose) {
                statement.execute(insert);
                System.out.println("Inserted");
            }

        }
        catch (Exception e) {
            System.out.println("addToDB exception happened " + e.getMessage());
        }
    }

    /**
     * Adds quotes in text
     * @param input input text
     * @return text with quotes
     */
    private String addQuotes(String input) {
        if (input != null && !input.isEmpty()) {
            String newText = "";
            for (int i = 0; i < input.length(); i++) {

                if (input.charAt(i) == '"') {
                    newText = newText + " ";
                } else {
                    newText = newText + input.charAt(i);
                }
            }
            return newText;
        }
        return input;
    }


}
