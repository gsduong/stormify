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
public class Supervisor {
    private final String host;
    private final String id;
    private final String uptime;
    
    public Supervisor(String _host, String _id, String _uptime) {
        this.host = _host;
        this.id = _id;
        this.uptime = _uptime;
    }
    
    public String getHost(){
        return this.host;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getUptime(){
        return this.uptime;
    }
    
    @Override
    public String toString() {
        return "Supervisor host: " + this.host + " - Uptime: " + this.uptime + "\n";
    }
}
