package com.yourcoast.yourcoastandroid.AccessPointData;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListItemReader {
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<ListItemStructure> read(InputStream listInputStream) throws JSONException {
        List<ListItemStructure> jList = new ArrayList<ListItemStructure>();
        String listjson = new Scanner(listInputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        Log.d("listjson", listjson);
        JSONArray array = new JSONArray(listjson);
        for (int i = 0; i < array.length(); i++) {
            String name = null;
            String description = null;
            JSONObject object = array.getJSONObject(i);
            int id = object.getInt("ID");
            Log.d("id", String.valueOf(id));
            if (!object.isNull("NameMobileWeb")) {
                name = object.getString("NameMobileWeb");
            }
            if (!object.isNull("DescriptionMobileWeb")) {
                description = object.getString("DescriptionMobileWeb");
            }
            jList.add(new ListItemStructure(name, description, id));
        }
        return jList;
    }

}

