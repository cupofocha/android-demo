package com.example.movies;

import static com.example.movies.Game.highScoreSPName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

public class HighScore extends AppCompatActivity {

    private SharedPreferences highScores;
    private LinearLayout linearLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        linearLayout = findViewById(R.id.high_scores);

        highScores = getSharedPreferences(
                highScoreSPName, MODE_PRIVATE);

        Map<String, ?> prefsMap = highScores.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            TextView textView = new TextView(this);
            textView.setText(entry.getKey()+": "+entry.getValue());
            linearLayout.addView(textView);
        }
    }
}