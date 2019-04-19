/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.interfaces;

import com.viettel.models.ClusterSummary;
import com.viettel.models.Supervisor;
import com.viettel.models.Topology;
import java.util.ArrayList;

/**
 *
 * @author vtt-cntt-l-51
 */
public interface StormServiceInterface {

    /* abstract methods */
    public String getHost();

    public int getUIPort();

    public String getAPIBaseUrl();

    /* functions and utilities */
    /* retrieve storm cluster data via rest api */
    public ClusterSummary getClusterSummary();
    public ArrayList<Supervisor> getSupervisorsList();
    public ArrayList<String> getTopoIdList();
    public Topology getTopologyById(String topoId);
    public ArrayList<Topology> getTopologies();
    public String toString(ClusterSummary cs, ArrayList<Supervisor> supervisors, ArrayList<Topology> topologies);
}
