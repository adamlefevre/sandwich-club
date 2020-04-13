package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI(Sandwich sandwich) {
        /*
          Get values from Sandwich and cast to strings
         */
        String alsoKnownAs = sandwich.getAlsoKnownAs().toString();
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();
        String ingredients = sandwich.getIngredients().toString();

        /*
          Connect to view objects
         */
        TextView akaLabel = findViewById(R.id.aka_label);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView originLabel = findViewById(R.id.origin_label);
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);

        /*
          Print formatted strings to views
         */
        if (alsoKnownAs != null && !alsoKnownAs.equals("[]")) {
            akaLabel.setVisibility(View.VISIBLE);
            alsoKnownTv.setVisibility(View.VISIBLE);
            String aka_format = alsoKnownAs.replace("[", "  * ").replace("]", "").replace(",", "\n  * ");
            alsoKnownTv.setText(aka_format);
        }

        if (placeOfOrigin != null && !placeOfOrigin.equals("")) {
            originLabel.setVisibility(View.VISIBLE);
            placeOfOriginTv.setVisibility(View.VISIBLE);
            placeOfOriginTv.setText(placeOfOrigin);
        }

        descriptionTv.setText(description);

        String ingredients_format = ingredients.replace("[", "  * ").replace("]", "").replace(", ", "\n  * ");
        ingredientsTv.setText(ingredients_format);
    }
}
