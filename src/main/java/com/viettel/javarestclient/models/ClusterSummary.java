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
public class ClusterSummary {
    private final String stormVersion;
    private final int topologies;
    private final int supervisors;
    
    public ClusterSummary(String _version, int _topologies, int _supervisors) {
        this.stormVersion = _version;
        this.topologies = _topologies;
        this.supervisors = _supervisors;
    }
    
    public String getStormVersion() {
        return this.stormVersion;
    }
    
    public int getNumberOfTopologies(){
        return this.topologies;
        
    }
    
    public int getNumberOfSupervisors(){
        return this.supervisors;
    }

    @Override
    public String toString() {
        return "Storm version: " + this.stormVersion + " - Running topologies: " + this.topologies + " - Running supervisors: " + this.supervisors + "\n";
    }
    
    
}
