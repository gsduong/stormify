/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.models;

import org.json.JSONObject;

/**
 *
 * @author vtt-cntt-l-51
 */
public class ApiResponse {
    final int code;
    final JSONObject json;
    
    public ApiResponse (int code, JSONObject json){
        this.code = code;
        this.json = json;
    }
    
    public int getResponseCode() {
        return this.code;
    }
    
    public JSONObject getJsonResponse(){
        return this.json;
    }
}
