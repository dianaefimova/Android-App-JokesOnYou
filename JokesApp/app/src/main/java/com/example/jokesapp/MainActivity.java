package com.example.jokesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void moveToProgramming(View view) {
        Intent openProgramming = new Intent(this, ProgrammingJoke.class);
        startActivity(openProgramming);
    }

    public void moveToSpooky(View view) {
        Intent openSpooky = new Intent(this, SpookyJoke.class);
        startActivity(openSpooky);
    }


    public void moveToChristmas(View view) {
        Intent openChristmas = new Intent(this, ChristmasJoke.class);
        startActivity(openChristmas);
    }

    public void moveToPun(View view) {
        Intent openPun = new Intent(this, PunJoke.class);
        startActivity(openPun);
    }


    public void moveToRandom(View view) {
        Intent openRandom = new Intent(this, RandomJoke.class);
        startActivity(openRandom);
    }

    public void moveToMemes(View view) {
        Intent openSaved = new Intent(this, MemesJoke.class);
        startActivity(openSaved);
    }
}