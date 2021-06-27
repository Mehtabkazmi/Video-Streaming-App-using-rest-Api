package com.example.javaproject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class JsonToPojo {
    public JsonToPojo() {
    }

    public String convertJsonArrayToArray(JSONArray data) {
        ArrayList<String> list = new ArrayList<String>();
        JSONArray jArray = (JSONArray) data;
        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    list.add(jArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list.toString();
    }
}
