package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * Converts a JSONArray into a List<String> object.
     */
    private static List<String> convertJsonArrayToStringList(JSONArray jsonArray)
            throws JSONException {
        List<String> result = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }
        return result;
    }

    /**
     * Converts a JSON string into a Sandwich object.
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichJson = new JSONObject(json);

        /*
          Name node contains mainName & alsoKnownAs
         */
        JSONObject names = sandwichJson.getJSONObject("name");

        String mainName = names.getString("mainName");
        JSONArray akaArray = names.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = convertJsonArrayToStringList(akaArray);

        String placeOfOrigin = sandwichJson.getString("placeOfOrigin");

        String description = sandwichJson.getString("description");

        String image = sandwichJson.getString("image");

        JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
        List<String> ingredients = convertJsonArrayToStringList(ingredientsArray);

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
