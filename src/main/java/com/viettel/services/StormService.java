/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.services;

import com.viettel.interfaces.StormServiceInterface;
import com.viettel.models.ApiResponse;
import com.viettel.models.Bolt;
import com.viettel.models.ClusterSummary;
import com.viettel.models.Spout;
import com.viettel.models.Supervisor;
import com.viettel.models.TopoStat;
import com.viettel.models.Topology;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vtt-cntt-l-51
 */
public class StormService implements StormServiceInterface {

    private final String host;
    private final int uiPort;
    private final String apiBaseUrl;

    public StormService(String host, int uiPort) {
        this.host = host;
        this.uiPort = uiPort;
        this.apiBaseUrl = new StringBuilder().append("http://").append(this.host).append(":").append(this.uiPort).append("/api/v1/").toString();
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getUIPort() {
        return this.uiPort;
    }

    @Override
    public String getAPIBaseUrl() {
        return this.apiBaseUrl;
    }

    @Override
    public ClusterSummary getClusterSummary() {
        ApiResponse clusterSummaryResponse = this.getApiResponse(this.getAPIBaseUrl() + "cluster/summary");
        if (clusterSummaryResponse == null || clusterSummaryResponse.getResponseCode() != 200) {
            return null;
        }
        JSONObject response = clusterSummaryResponse.getJsonResponse();
        return new ClusterSummary(response.getString("stormVersion"), response.getInt("topologies"), response.getInt("supervisors"));
    }

    @Override
    public ArrayList<Supervisor> getSupervisorsList() {
        ApiResponse clusterSummaryResponse = this.getApiResponse(this.getAPIBaseUrl() + "supervisor/summary");
        if (clusterSummaryResponse == null || clusterSummaryResponse.getResponseCode() != 200) {
            return null;
        }
        JSONObject myObj = clusterSummaryResponse.getJsonResponse();
        JSONArray array = myObj.getJSONArray("supervisors");
        ArrayList<Supervisor> supervisors = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject e = (JSONObject) array.get(i);
            supervisors.add(new Supervisor(e.getString("host"), e.getString("id"), e.getString("uptime")));
        }
        return supervisors;
    }

    @Override
    public ArrayList<String> getTopoIdList() {
        ApiResponse clusterSummaryResponse = this.getApiResponse(this.getAPIBaseUrl() + "topology/summary");
        if (clusterSummaryResponse == null || clusterSummaryResponse.getResponseCode() != 200) {
            return null;
        }
        JSONObject myObj = clusterSummaryResponse.getJsonResponse();
        JSONArray array = myObj.getJSONArray("topologies");
        ArrayList<String> topoIdList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject e = (JSONObject) array.get(i);
            topoIdList.add(e.getString("id"));
        }
        return topoIdList;
    }

    @Override
    public Topology getTopologyById(String topoId) {
        ApiResponse clusterSummaryResponse = this.getApiResponse(this.getAPIBaseUrl() + "topology/" + topoId);
        if (clusterSummaryResponse == null || clusterSummaryResponse.getResponseCode() != 200) {
            return null;
        }
        JSONObject myObj = clusterSummaryResponse.getJsonResponse();
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
            if (e.getString("windowPretty").equals("1d 0h 0m 0s") || e.getString("windowPretty").equals("All time")) {
            // do nothing
            //   to monitor only last 3 running hours of topo 
            } else topoStats.add(new TopoStat(e.getString("windowPretty"), e.getString("window"), e.getInt("emitted"), e.getInt("transferred"), e.getString("completeLatency"), e.getInt("acked"), failed));
        }
        Topology toAdd = new Topology(name, topoId, uptime, status, workersTotal, spouts, bolts, topoStats);
        return toAdd;
    }

    @Override
    public ArrayList<Topology> getTopologies() {
        ArrayList<Topology> topologies = new ArrayList<>();
        ArrayList<String> topoIdList = this.getTopoIdList();
        topoIdList.forEach((topoId) -> {
            boolean add = topologies.add(this.getTopologyById(topoId));
        });
        return topologies;
    }

    @Override
    public String toString(ClusterSummary cs, ArrayList<Supervisor> supervisors, ArrayList<Topology> topologies) {
        StringBuilder sb = new StringBuilder();
        if (cs == null) {
            sb.append("Cannot retrieve data from storm cluster\n");
        } else {
            sb.append(cs.toString());
            supervisors.forEach((supervisor) -> {
                sb.append(supervisor.toString());
            });
            topologies.forEach((topology) -> {
                sb.append(topology.toString());
            });
        }

        return sb.toString();
    }

    private ApiResponse getApiResponse(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new ApiResponse(responseCode, new JSONObject(response.toString()));

        } catch (MalformedURLException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
}
