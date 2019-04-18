/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.javarestclient.models;

/**
 *
 * @author vtt-cntt-l-51
 */
public class Bolt {
    private final int emitted;
    private final String boltId;
    private final String processLatency;
    private final String executeLatency;
    private final int transferred;
    private final int acked;
    private final int executed;

    public Bolt(int emitted, String boltId, String processLatency, String executeLatency, int transferred, int acked, int executed) {
        this.emitted = emitted;
        this.boltId = boltId;
        this.processLatency = processLatency;
        this.executeLatency = executeLatency;
        this.transferred = transferred;
        this.acked = acked;
        this.executed = executed;
    }
    


    public int getEmitted() {
        return emitted;
    }

    public String getBoltId() {
        return boltId;
    }

    public String getProcessLatency() {
        return processLatency;
    }

    public String getExecuteLatency() {
        return executeLatency;
    }

    public int getTransferred() {
        return transferred;
    }

    public int getAcked() {
        return acked;
    }

    public int getExecuted() {
        return executed;
    }
    
}
