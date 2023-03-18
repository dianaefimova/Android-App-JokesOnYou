package com.example.jokesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RandomJoke extends AppCompatActivity {
    private static final String API_URL = "https://v2.jokeapi.dev/joke/";
    private TextView jokeTextView;
    private TextView categoryTextView;
    private String Random;


    private static final String RANDOM_KEY = "random_key";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_joke);

        jokeTextView = findViewById(R.id.jokeRandom);
        Button refreshButton = findViewById(R.id.refreshRandom);
        categoryTextView = findViewById(R.id.jokeCategory);


        if (savedInstanceState != null) {
            Random = savedInstanceState.getString(RANDOM_KEY);
            jokeTextView.setText(Random);
        } else {
            refreshJoke("Any");
        }

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshJoke("Any"); // Getting random joke with this category every time
            }
        });

        refreshJoke("Any");
    }

    private void refreshJoke(String categories) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = API_URL + categories + "?safe-mode";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String type = response.getString("type");
                            String joke = "";
                            String category = "";
                            if (type.equals("single")) {
                                joke = response.getString("joke");
                            } else if (type.equals("twopart")) {
                                joke = response.getString("setup") + "\n\n" +
                                        response.getString("delivery");
                            }
                            category = response.getString("category");

                            categoryTextView.setText("Category: "  + category);
                            jokeTextView.setText(joke);
                        } catch (JSONException e) {
                            jokeTextView.setText("Failed to load joke.");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jokeTextView.setText("Failed to load joke.");
            }
        });

        queue.add(jsonObjectRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(new Bundle());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RANDOM_KEY, Random);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Random = savedInstanceState.getString(RANDOM_KEY);
        jokeTextView.setText(Random);
    }
    public void Back(View view) {
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }
}