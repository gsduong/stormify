/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.stormify;

import com.viettel.services.StormService;
import java.util.ArrayList;

/**
 *
 * @author vtt-cntt-l-51
 */
public class Main {

    public static String main(String args[]) {
        
        if (args.length < 2) {
            System.out.println("Invalid number of arguments: run with <arg1=host> <arg2=port> <arg3=topo_name> ...");
            return null;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        
        /* get target topologies as filter*/
        ArrayList<String> topologies = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (i>1) {
                // ignore args[0] and args[1]
                topologies.add(args[i].toLowerCase());
            }
        }
        StormService service = new StormService(host, port, topologies);
        String result;
        if (service.getClusterSummary() == null) {
            result = "Cannot retrieve data from API endpoint: http://" + host + ":" + port + "/api/v1/<br>Check cluster status!!!<br>";
            
        } else {
            result = service.toString(service.getClusterSummary(), service.getSupervisorsList(), service.getTopologies());          
        }
        System.out.println(result);
        return result;
    }

}
