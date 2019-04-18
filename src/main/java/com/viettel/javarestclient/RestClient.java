/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.javarestclient;

/**
 *
 * @author vtt-cntt-l-51
 */
public class RestClient {

    public static String main(String args[]) {
        
        if (args.length != 2) {
            System.out.println("Invalid number of arguments: run with <arg1=host> <arg2=port>");
            return null;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        RestService restService = new RestService(host, port );
        String result;
        if (restService.getClusterSummary() == null) {
            result = "Cannot retrieve data from API endpoit: http://" + host + ":" + port + "/api/v1/\n";
            
        } else {
            result = restService.toString(restService.getClusterSummary(), restService.getSupervisorsList(), restService.getTopologies());          
        }
        System.out.println(result);
        return result;
    }

}
