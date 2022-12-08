package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    public static String sharedPrefName = "com.example.movies.genre";
    public static String keyName = "genre";
    private SharedPreferences sharedPreferences;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getSharedPreferences(sharedPrefName, MODE_PRIVATE);

        radioGroup = findViewById(R.id.radioGroup);
    }

    public void onClickSave(View view){
        RadioButton selectedButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        String selected = selectedButton.getText().toString();
        switch (selected){
            case "Action":
                sharedPreferences.edit().putString(keyName, "action").apply();
                break;
            case "Comedy":
                sharedPreferences.edit().putString(keyName, "comedy").apply();
                break;
            case "Sci-Fi":
                sharedPreferences.edit().putString(keyName, "sci-fi").apply();
                break;
            default:
                sharedPreferences.edit().putString(keyName, "action").apply();
                break;
        }
        Intent intent = new Intent(Settings.this,
                MainActivity.class);
        startActivity(intent);
    }
}