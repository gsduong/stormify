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
public class Spout {
    private final String spoutId;
    private final int emitted;
    private final int failed;
    private final String completeLatency;
    private final int transfered;
    private final int acked;

    public Spout(String spoutId, int emitted, int failed, String completeLatency, int transfered, int acked) {
        this.spoutId = spoutId;
        this.emitted = emitted;
        this.failed = failed;
        this.completeLatency = completeLatency;
        this.transfered = transfered;
        this.acked = acked;
    }



    public String getSpoutId() {
        return spoutId;
    }

    public int getEmitted() {
        return emitted;
    }

    public int getFailed() {
        return failed;
    }

    public String getCompleteLatency() {
        return completeLatency;
    }

    public int getTransfered() {
        return transfered;
    }

    public int getAcked() {
        return acked;
    }
    
    
}
