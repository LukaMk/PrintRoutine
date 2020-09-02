package com.ideabytes.tse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown=true)
@JsonIgnoreProperties({ "conn","verbose","database" })
/*******************************************************************
 * Ideabytes Inc.
 * Luka Macieszczak
 * July 28th, 2020
 * Purpose: Gets information from methods in other methods to put into a database and JSON object
 ********************************************************************/
public class TSEHandler {
    private String appName;
    private List<TSETracer> tseTracer;
    Connection conn = null;
    String dataBase;
    boolean verbose;

    public void setTseTracer(List<TSETracer> tseTracer) {
        this.tseTracer = tseTracer;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public Connection getConn() {
        return conn;
    }

    public String getDataBase() {
        return dataBase;
    }

    /**
     * Constructor
     * @param appName Name of application
     * @param dataBase Connection string fro database
     */
    public TSEHandler(String appName, String dataBase, boolean verbose ) {
        this.appName = appName;
        this.verbose = verbose;
        if (dataBase != null) {
            this.dataBase = dataBase;
        }
        else {
            this.dataBase = "jdbc:sqlite:C:\\Databases\\TSEOutput.db";
        }
        try {
            conn = DriverManager.getConnection(this.dataBase);
        } catch (Exception e) {
            System.out.println(e);
        }
        this.tseTracer = new ArrayList<>();
    }

    /**
     * gets verbose
     * @return verbose
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * sets verbose
     * @param verbose
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    public String getAppName() {
        return appName;
    }

    public List<TSETracer> getTseTracer() {
        return tseTracer;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setTseTracker(List<TSETracer> tseTracker) {
        this.tseTracer = tseTracer;
    }

    /**
     * Adds method to TSETracker
     * @param className name of class
     * @param methodName name of method
     * @param inputparams parameters of method
     */
    public void addMethod(String className, String methodName,List<Parameter> inputparams) {
        if (tseTracer!= null) {
            tseTracer.add(new TSETracer(className, methodName, inputparams));
        }
    }

    /**
     * Returns result
     * @param className name of class
     * @param methodName name of method
     * @param result what the method does and its return value
     */
    public void setReturnForResult(String className, String methodName, Result result) {
        for (TSETracer tracer : tseTracer) {
            if (tracer.getClassName().equals(className)&&tracer.getMethodName().equals(methodName) && tracer.getResult() == null){
                tracer.setResult(result);
                return;
            }
        }
    }

    /**
     * Opens Db
     * Adds to DB
     * Closes DB
     */
    public void close() throws Exception {
        Statement statement = DBHandles();
        for (TSETracer tracer : tseTracer) {
            // add line into db
            try {
                tracer.addToDB(statement, this.appName, this.verbose);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        closeDB();
        if (verbose) {
            ObjectMapper mapper = new ObjectMapper();
            String test = mapper.writeValueAsString(this);
            System.out.println(test);
            FileWriter write = new FileWriter("C:\\Users\\luka\\Documents\\JSONOutput\\TSETracker.txt", true);
            PrintWriter print_line = new PrintWriter(write);
            print_line.printf("%s", mapper.writeValueAsString(this));
            print_line.close();
        }


    }

    @Override
    public String toString() {
        return "TSEHandler{" +
                "appName='" + appName + '\'' +
                ", tseTracer=" + tseTracer +
                '}';
    }

    private Statement DBHandles(){
        try {

            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS TSE2(appName VARCHAR(1024),startTime LONG, className VARCHAR(1024), methodName VARCHAR(1024), inputParams VARCHAR(1024), result VARCHAR(1024))");
            //ConstantsEntity dbString = new ConstantsEntity(eventTime,endTime,className,methodName,params,result);
            return statement;

        }
        catch (Exception e){
            System.out.println(e);

        }
        return null;
    }

    private void closeDB() {
        try {
            conn.close();
        } catch (Exception e){
            System.out.println(e);
        }

    }
}
