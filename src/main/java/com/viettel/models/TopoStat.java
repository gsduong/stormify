/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.models;

/**
 *
 * @author vtt-cntt-l-51
 */
public class TopoStat {
    private final String windowPretty;
    private final String window;
    private final int emitted;
    private final int transferred;
    private final String completeLatency;
    private final int acked;
    private final int failed;

    public TopoStat(String windowPretty, String window, int emitted, int transferred, String completeLatency, int acked, int failed) {
        this.windowPretty = windowPretty;
        this.window = window;
        this.emitted = emitted;
        this.transferred = transferred;
        this.completeLatency = completeLatency;
        this.acked = acked;
        this.failed = failed;
    }
    


    public String getWindowPretty() {
        return windowPretty;
    }

    public String getWindow() {
        return window;
    }

    public int getEmitted() {
        return emitted;
    }

    public int getTransferred() {
        return transferred;
    }

    public String getCompleteLatency() {
        return completeLatency;
    }

    public int getAcked() {
        return acked;
    }

    public int getFailed() {
        return failed;
    }

    @Override
    public String toString() {
        return "Window: " + this.getWindowPretty() + " - Emitted: " + this.getEmitted() + " - Transferred: " + this.getTransferred() + " - Acked: " + this.getAcked() + " - Failed: " + this.getFailed() + "\n";          
    }
    
    
    
}
