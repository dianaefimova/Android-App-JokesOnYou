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

public class SpookyJoke extends AppCompatActivity {
    private static final String API_URL = "https://v2.jokeapi.dev/joke/Spooky?safe-mode";
    private TextView jokeTextView;
    private String Spooky;
    private static final String SPOOKY_KEY = "spooky_key";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spooky_joke);

        jokeTextView = findViewById(R.id.jokeSpooky);
        Button refreshButton = findViewById(R.id.refreshSpooky);


        if (savedInstanceState != null) {
            Spooky = savedInstanceState.getString(SPOOKY_KEY);
            jokeTextView.setText(Spooky);
        } else {
            refreshJoke("Spooky,Pun");
        }

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshJoke("Spooky,Pun"); // Getting random joke with this category every time
            }
        });

        refreshJoke("Spooky,Pun");
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
        outState.putString(SPOOKY_KEY, Spooky);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Spooky = savedInstanceState.getString(SPOOKY_KEY);
        jokeTextView.setText(Spooky);
    }

    public void Back(View view) {
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }
}

