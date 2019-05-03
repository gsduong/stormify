/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.models;

import java.sql.Timestamp;


/**
 *
 * @author vtt-cntt-l-51
 */
public class Warning {
    private final String topoName;
    private final String clusterIp;
    private final Timestamp monitorAt;
    private final String windowTime;
    private final int emitted;
    private final int transferred;
    private final int acked;
    private final int failed;

    public Warning(String topoName, String clusterIp, Timestamp monitorAt, String windowTime, int emitted, int transferred, int acked, int failed) {
        this.topoName = topoName;
        this.clusterIp = clusterIp;
        this.monitorAt = monitorAt;
        this.windowTime = windowTime;
        this.emitted = emitted;
        this.transferred = transferred;
        this.acked = acked;
        this.failed = failed;
    }

    public String getTopoName() {
        return topoName;
    }

    public String getClusterIp() {
        return clusterIp;
    }

    public Timestamp getMonitorAt() {
        return monitorAt;
    }

    public String getWindowTime() {
        return windowTime;
    }

    public int getEmitted() {
        return emitted;
    }

    public int getTransferred() {
        return transferred;
    }

    public int getAcked() {
        return acked;
    }

    public int getFailed() {
        return failed;
    }

    @Override
    public String toString() {
        return this.getTopoName() + " " + this.getClusterIp() + " " + this.getMonitorAt() + " " + this.getWindowTime() + " " + this.getEmitted() + " " + this.getTransferred() + " " + this.getAcked() + " " + this.getFailed() + "\n";
    }
    
    
}
