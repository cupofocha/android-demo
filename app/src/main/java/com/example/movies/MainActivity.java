package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "46b9da76";
    EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.user_name);
    }

    public void onClickStart(View view) {
        if (!userName.getText().toString().isEmpty()) {
            Intent intent = new Intent(MainActivity.this,
                    Game.class);
            intent.putExtra("userName",userName.getText().toString());
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Please input your name!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSetting(View view){
        Intent intent = new Intent(MainActivity.this,
                Settings.class);
        startActivity(intent);
    }

    public void onClickHighScore(View view){
        Intent intent = new Intent(MainActivity.this,
                HighScore.class);
        startActivity(intent);
    }

}