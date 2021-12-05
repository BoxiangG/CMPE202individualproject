package com.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class database {
    private static database instance;
    public Set<String> CreditCard_Set;
    public HashMap<String, Integer> quantity;
    public HashMap<String, String> category;
    public HashMap<String, Double> price;
    public HashMap<String, Integer> limit;

    private database() {
        CreditCard_Set = new HashSet<String>();
        quantity = new HashMap<>();
        category = new HashMap<>();
        price = new HashMap<>();
        limit = new HashMap<>();
        addlimit(limit);
    }
    // hardcode category limit
    private void addlimit(HashMap<String, Integer> limit){
        limit.put("Luxury",3);
        limit.put("Essential",5);
        limit.put("Misc",6);
    }

    public static database getInstance() {
        if (instance == null) {
            instance = new database();
        }
        return instance;
    }
}