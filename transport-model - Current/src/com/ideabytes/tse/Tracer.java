package com.ideabytes.tse;

/*******************************************************************
 * Ideabytes Inc.
 * Luka Macieszczak
 * July 28th, 2020
 * Purpose: Utility class for TSEHandler, every class which uses the handler must extend this class
 ********************************************************************/
public class Tracer {
    public TSEHandler tseHandler;

    public void setTseHandler(TSEHandler handler) {
        this.tseHandler = handler;
    }

    public TSEHandler getTseHandler() {
        return tseHandler;
    }
}
