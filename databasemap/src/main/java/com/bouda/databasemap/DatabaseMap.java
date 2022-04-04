package com.bouda.databasemap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseMap {

    public static final String TABLES = "tables";
    public static final String FIELDS = "fields";
    public static final String COLS = "fields";
    public static final String CONSTR = "constraints";

    private HashMap<String, Map<String, Map<String, ?>>> map;

    public DatabaseMap(HashMap<String, Map<String, Map<String, ?>>> map){
        this.map = map;
    }

    public List<String> getTables(){
        return new ArrayList<>(this.map.keySet());
    }

    public HashMap<String, Map<String, Map<String, ?>>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Map<String, Map<String, ?>>> map) {
        this.map = map;
    }
}
