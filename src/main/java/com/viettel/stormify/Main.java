/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.stormify;

import com.viettel.services.StormService;

/**
 *
 * @author vtt-cntt-l-51
 */
public class Main {

    public static String main(String args[]) {
        
        if (args.length != 2) {
            System.out.println("Invalid number of arguments: run with <arg1=host> <arg2=port>");
            return null;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        StormService service = new StormService(host, port );
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
