/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.javarestclient.models;

import java.util.ArrayList;

/**
 *
 * @author vtt-cntt-l-51
 */
public class Topology {
    private final String id;
    private final String uptime;
    private final String status;
    private final int workersTotal;
    private final String name;
    
    private final ArrayList<Spout> spouts;
    private final ArrayList<Bolt> bolts;
    
    private final ArrayList<TopoStat> topoStats;

    public Topology(String name, String id, String uptime, String status, int workersTotal, ArrayList<Spout> spouts, ArrayList<Bolt> bolts, ArrayList<TopoStat> topoStats) {
        this.name = name;
        this.id = id;
        this.uptime = uptime;
        this.status = status;
        this.workersTotal = workersTotal;
        this.spouts = spouts;
        this.bolts = bolts;
        this.topoStats = topoStats;
    }

    public String getName(){
        return name;
    }
    
    public String getId() {
        return id;
    }

    public String getUptime() {
        return uptime;
    }

    public String getStatus() {
        return status;
    }

    public int getWorkersTotal() {
        return workersTotal;
    }

    public ArrayList<Spout> getSpout() {
        return spouts;
    }

    public ArrayList<Bolt> getBolt() {
        return bolts;
    }

    public ArrayList<TopoStat> getTopoStats() {
        return topoStats;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append("\n");
        
        this.getTopoStats().forEach((topoStat) -> {
            StringBuilder append = sb.append(topoStat.toString());
        });
        
        return sb.toString();
    }
    
    
    
}
