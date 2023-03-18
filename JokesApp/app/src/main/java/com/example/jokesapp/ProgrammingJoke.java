package com.example.jokesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProgrammingJoke extends AppCompatActivity {
    private static final String API_URL = "https://v2.jokeapi.dev/joke/Programming?safe-mode";
    private TextView jokeTextView;
    private String program;
    private static final String PROGRAM_KEY = "program_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_joke);

        jokeTextView = findViewById(R.id.jokeProgramming);
        Button refreshButton = findViewById(R.id.refreshProgramming);

        if (savedInstanceState != null) {
            program = savedInstanceState.getString(PROGRAM_KEY);
            jokeTextView.setText(program);
        } else {
            refreshJoke("Programming,Pun");
        }

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshJoke("Programming,Pun");
            }
        });

    }

    private void refreshJoke(String categories) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = API_URL + categories + "&safe-mode";

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
                            program = joke;
                            jokeTextView.setText(program);
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
        outState.putString(PROGRAM_KEY, program);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        program = savedInstanceState.getString(PROGRAM_KEY);
        jokeTextView.setText(program);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}