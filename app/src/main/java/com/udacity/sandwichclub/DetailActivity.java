package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView sandwitchImageIv;
    private TextView originTv;
    private TextView alsoKnownTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sandwitchImageIv = findViewById(R.id.image_iv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);


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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Set title of sandwich in toolbar
        setTitle(sandwich.getMainName());

        // Set image of sandwich
        // Check if image is provided, if not show dummy image
        if (sandwich.getImage() != null) {
            Picasso.get()
                    .load(sandwich.getImage())
                    .into(sandwitchImageIv);
        } else {
            // Set dummy image if no imageView for sandwich provided
            Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .into(sandwitchImageIv);
        }

        // Check if origin is provided, if not inform that no origin known
        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().equals("") ) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTv.setText(R.string.origin_not_known);
        }

        // Check if also known as is provided, if not leave empty (-)
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs != null && alsoKnownAs.size()!=0) {
                    for (int i = 0; i < alsoKnownAs.size(); i++) {
                        alsoKnownTv.append(alsoKnownAs.get(i)+ "\n");
                    }
            String alsoKnownText = alsoKnownTv.getText().toString();
            alsoKnownTv.setText(alsoKnownText.trim());
        } else alsoKnownTv.setText(R.string.no_also_known);

        // Check if description is provided, if not inform that no description provided
        if (sandwich.getDescription() != null && !sandwich.getDescription().equals("") ) {
            descriptionTv.setText(sandwich.getDescription());
        } else {
            descriptionTv.setText(R.string.no_description);
        }

        // Check if ingredients is provided, if not inform that no ingredients provided
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList != null && ingredientsList.size()!=0) {
            for (int i = 0; i < ingredientsList.size(); i++) {
                ingredientsTv.append(ingredientsList.get(i)+"\n");
            }
        } else {
            ingredientsTv.setText(R.string.ingredients_not_provided);
        }
    }
}
