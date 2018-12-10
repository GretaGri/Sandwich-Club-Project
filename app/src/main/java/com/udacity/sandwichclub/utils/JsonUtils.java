package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        // Create an empty Sandwich
        Sandwich sandwich = new Sandwich();
        if (json != null) {
            // Try to parse the JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {
                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Object name
                JSONObject name = jsonObj.getJSONObject("name");

                // Getting String mainName
                String mainName = name.getString("mainName");

                // Getting array of Strings alsoKnownAs
                JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
                List<String> alsoKnownAsList = new ArrayList<>();

                if (alsoKnownAs != null) {

                    // looping through alsoKnownAs
                    for (int i = 0; i < alsoKnownAs.length(); i++) {
                        String alsoKnownAsString = alsoKnownAs.getString(i);
                        alsoKnownAsList.add(alsoKnownAsString);
                    }
                }
                // Getting String placeOfOrigin
                String placeOfOrigin = jsonObj.getString("placeOfOrigin");

                // Getting String description
                String description = jsonObj.getString("description");

                // Getting String for image resource
                String image = jsonObj.getString("image");

                // Getting array of Strings for ingredients
                JSONArray ingredients = jsonObj.getJSONArray("ingredients");
                List<String> ingredientList = new ArrayList<>();

                if (ingredients != null) {

                    // looping through ingredients
                    for (int i = 0; i < ingredients.length(); i++) {
                        String ingredient = ingredients.getString(i);
                        ingredientList.add(ingredient);
                    }
                }

                // Fill the Sandwich! :) Adding info about one sandwich to Sandwich object
                sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientList);

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return sandwich;
    }
}