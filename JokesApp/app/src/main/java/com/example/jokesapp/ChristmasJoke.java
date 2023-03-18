package com.example.jokesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChristmasJoke extends AppCompatActivity {
    private static final String API_URL = "https://v2.jokeapi.dev/joke/";
    private TextView jokeTextView;
    private String Christmas;
    private static final String CHRISTMAS_KEY = "christmas_key";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas_joke);

        jokeTextView = findViewById(R.id.jokeChristmas);
        Button refreshButton = findViewById(R.id.refreshChristmas);

        if (savedInstanceState != null) {
            Christmas = savedInstanceState.getString(CHRISTMAS_KEY);
            jokeTextView.setText(Christmas);
        } else {
            refreshJoke("Christmas");
        }

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshJoke("Christmas"); // Getting random joke with this category every time
            }
        });

        refreshJoke("Christmas");
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
                            if (type.equals("single")) {
                                joke = response.getString("joke");
                            } else if (type.equals("twopart")) {
                                joke = response.getString("setup") + "\n\n" +
                                        response.getString("delivery");
                            }
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
        outState.putString(CHRISTMAS_KEY, Christmas);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Christmas = savedInstanceState.getString(CHRISTMAS_KEY);
        jokeTextView.setText(Christmas);
    }
    public void Back(View view) {
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    }
