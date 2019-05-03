/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.stormify;

import com.viettel.models.TopoStat;
import com.viettel.models.Topology;
import com.viettel.models.Warning;
import com.viettel.services.StormService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author vtt-cntt-l-51
 */
public class StormWarning {

    public static String main(String args[]) {
        if (args.length < 2) {
            System.out.println("Invalid number of arguments: run with <arg1=host> <arg2=port> <arg3=topo_name> ...");
//            return null;
            return "Invalid number of arguments: run with <arg1=host> <arg2=port> <arg3=topo_name> ...";
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        /* get target topologies as filter*/
        ArrayList<String> topologies = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (i > 1) {
                // ignore args[0] and args[1]
                topologies.add(args[i].toLowerCase());
            }
        }
        StormService service = new StormService(host, port, topologies);
        String result;
        if (service.getClusterSummary() == null) {
            result = "Cannot retrieve data from API endpoint: http://" + host + ":" + port + "/api/v1/<br>Check cluster status!!!<br>";
            return result;

        } else {
            boolean getFullTopoInfo = true;
            result = service.toString(service.getClusterSummary(), service.getSupervisorsList(), service.getTopologies(true));
            Timestamp currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            ArrayList<Warning> warningList = new ArrayList<>();
            ArrayList<Topology> topoList = new ArrayList<>();
            topoList = service.getTopologies(getFullTopoInfo);

            StringBuilder sb = new StringBuilder();
            for (Topology topology : topoList) {
                ArrayList<TopoStat> topoStats = new ArrayList<>();
                topoStats = topology.getTopoStats();

                for (TopoStat topoStat : topoStats) {
                    Warning toAdd = new Warning(topology.getName(), host, currentDate, topoStat.getWindowPretty(), topoStat.getEmitted(), topoStat.getTransferred(), topoStat.getAcked(), topoStat.getFailed());
                    warningList.add(toAdd);
                    sb.append("INSERT INTO storm_warning VALUES ('").append(topology.getName()).append("', '").append(host).append("', ").append("TO_TIMESTAMP('" + currentDate + "', 'YYYY-MM-DD HH24:MI:SS.FF')").append(", '").append(topoStat.getWindowPretty()).append("', '").append(topoStat.getEmitted()).append("', '").append(topoStat.getTransferred()).append("', '").append(topoStat.getAcked()).append("', '").append(topoStat.getFailed()).append("');\n");
                }
            }
            return sb.toString();
        }

    }
}
