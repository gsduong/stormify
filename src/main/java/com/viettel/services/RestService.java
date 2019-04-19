/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.viettel.models.Bolt;
import com.viettel.models.ClusterSummary;
import com.viettel.models.Spout;
import com.viettel.models.Supervisor;
import com.viettel.models.TopoStat;
import com.viettel.models.Topology;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vtt-cntt-l-51
 */
public class RestService {

    private final String host;
    private final Integer uiPort;
    private final String apiBaseUrl;

    public RestService(String _host, Integer _uiPort) {
        this.host = _host;
        this.uiPort = _uiPort;
        this.apiBaseUrl = this.formAPIBaseUrl(this.host, this.uiPort);

    }

    /* getter & setter */
    public String getHost() {
        return this.host;
    }

    public Integer getUIPort() {
        return this.uiPort;
    }

    public String getAPIBaseUrl() {
        return this.apiBaseUrl;
    }

    private String formAPIBaseUrl(String _host, Integer _uiPort) {
        /* http://<ui-host>:<ui-port>/api/v1/ */
        return new StringBuilder().append("http://").append(_host).append(":").append(_uiPort).append("/api/v1/").toString();
    }

    /* functions and utilities */
 /* retrieve storm cluster data via rest api */
    public ClusterSummary getClusterSummary() {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(this.getAPIBaseUrl() + "cluster/summary")
                    .header("accept", "application/json")
                    .asJson();
            if (jsonResponse.getStatus() != 200) {
                return null;
            }
            JSONObject myObj = jsonResponse.getBody().getObject();
            ClusterSummary cs = new ClusterSummary(myObj.getString("stormVersion"), myObj.getInt("topologies"), myObj.getInt("supervisors"));
            return cs;
        } catch (UnirestException ex) {
//            Logger.getLogger(RestClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    public ArrayList<Supervisor> getSupervisorsList() {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(this.getAPIBaseUrl() + "supervisor/summary")
                    .header("accept", "application/json")
                    .asJson();
            if (jsonResponse.getStatus() != 200) {
                return null;
            }
            JSONObject myObj = jsonResponse.getBody().getObject();
            JSONArray array = myObj.getJSONArray("supervisors");
            ArrayList<Supervisor> supervisors = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject e = (JSONObject) array.get(i);
                supervisors.add(new Supervisor(e.getString("host"), e.getString("id"), e.getString("uptime")));
            }
            return supervisors;
        } catch (UnirestException ex) {
//            Logger.getLogger(RestClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ArrayList<String> getTopoIdList() {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(this.getAPIBaseUrl() + "topology/summary")
                    .header("accept", "application/json")
                    .asJson();
            if (jsonResponse.getStatus() != 200) {
                return null;
            }
            JSONObject myObj = jsonResponse.getBody().getObject();
            JSONArray array = myObj.getJSONArray("topologies");
            ArrayList<String> topoIdList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject e = (JSONObject) array.get(i);
                topoIdList.add(e.getString("id"));
            }
            return topoIdList;
        } catch (UnirestException ex) {
//            Logger.getLogger(RestClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Topology getTopologyById(String topoId) {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(this.getAPIBaseUrl() + "topology/" + topoId)
                    .header("accept", "application/json")
                    .asJson();
            if (jsonResponse.getStatus() != 200) {
                return null;
            }
            JSONObject myObj = jsonResponse.getBody().getObject();

            String name = myObj.getString("name");
            String uptime = myObj.getString("uptime");
            String status = myObj.getString("status");
            Integer workersTotal = myObj.getInt("workersTotal");

            /* get spouts */
            JSONArray spoutJsonArray = myObj.getJSONArray("spouts");
            ArrayList<Spout> spouts = new ArrayList<>();
            for (int i = 0; i < spoutJsonArray.length(); i++) {
                JSONObject e = (JSONObject) spoutJsonArray.get(i);
                spouts.add(new Spout(e.getString("spoutId"), e.getInt("emitted"), e.getInt("failed"), e.getString("completeLatency"), e.getInt("transferred"), e.getInt("acked")));
            }

            /* get bolts */
            JSONArray boltJsonArray = myObj.getJSONArray("bolts");
            ArrayList<Bolt> bolts = new ArrayList<>();
            for (int i = 0; i < boltJsonArray.length(); i++) {
                JSONObject e = (JSONObject) boltJsonArray.get(i);
                bolts.add(new Bolt(e.getInt("emitted"), e.getString("boltId"), e.getString("processLatency"), e.getString("executeLatency"), e.getInt("transferred"), e.getInt("acked"), e.getInt("executed")));
            }

            /* get topo stats */
            JSONArray topoStatJsonArray = myObj.getJSONArray("topologyStats");
            ArrayList<TopoStat> topoStats = new ArrayList<>();
            for (int i = 0; i < topoStatJsonArray.length(); i++) {
                JSONObject e = (JSONObject) topoStatJsonArray.get(i);
                int failed = 0;
                if (e.has("failed") && !e.isNull("failed")) {
                    failed = e.getInt("failed");
                }
                topoStats.add(new TopoStat(e.getString("windowPretty"), e.getString("window"), e.getInt("emitted"), e.getInt("transferred"), e.getString("completeLatency"), e.getInt("acked"), failed));
            }
//            return null;
            Topology toAdd = new Topology(name, topoId, uptime, status, workersTotal, spouts, bolts, topoStats);
            return toAdd;

        } catch (UnirestException ex) {
//            Logger.getLogger(RestClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public ArrayList<Topology> getTopologies() {
        ArrayList<Topology> topologies = new ArrayList<>();
        ArrayList<String> topoIdList = this.getTopoIdList();
        topoIdList.forEach((topoId) -> {
            boolean add = topologies.add(this.getTopologyById(topoId));
        });
        return topologies;
    }

    public String toString(ClusterSummary cs, ArrayList<Supervisor> supervisors, ArrayList<Topology> topologies) {
        StringBuilder sb = new StringBuilder();
        sb.append(cs.toString());
        supervisors.forEach((supervisor) -> {
            sb.append(supervisor.toString());
        });
        topologies.forEach((topology) -> {
            sb.append(topology.toString());
        });
        return sb.toString();
    }
}
