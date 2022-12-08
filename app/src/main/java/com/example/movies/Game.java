package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Game extends AppCompatActivity {

    List<String> action = new ArrayList<>();
    List<String> comedy = new ArrayList<>();
    List<String> sciFi = new ArrayList<>();
    List<JSONObject> movies = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences highScores;
    static String highScoreSPName = "com.example.movies.high_scores";
    int currentMovie = 0;
    int correctYear;
    boolean startFlag = false;
    String userName;

    Button year1;
    Button year2;
    Button year3;
    ImageView posterView;
    RequestQueue queue;

    int points;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        queue = Volley.newRequestQueue(Game.this);
        toast = new Toast(getApplicationContext());
        userName = getIntent().getExtras().getString("userName");

        posterView = findViewById(R.id.PosterView);
        year1 = findViewById(R.id.year1);
        year2 = findViewById(R.id.year2);
        year3 = findViewById(R.id.year3);
        sharedPreferences = getSharedPreferences(
                Settings.sharedPrefName, MODE_PRIVATE);
        highScores = getSharedPreferences(
                highScoreSPName, MODE_PRIVATE);

        Map<String, ?> prefsMap = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            Log.d("SharedPreferences", entry.getKey() + ":" +
                    entry.getValue().toString());
        }

        action.add("tt3498820");
        action.add("tt10321138");
        action.add("tt0371746");
        action.add("tt0293429");
        comedy.add("tt0095705");
        comedy.add("tt1637725");
        comedy.add("tt2637276");
        sciFi.add("tt0371724");
        sciFi.add("tt0062622");
        sciFi.add("tt4154664");

        Log.d("sb",userName);

        switch (sharedPreferences.getString(
                Settings.keyName, "")){
            case "comedy":
                getMovies(comedy);
                break;
            case "sci-fi":
                getMovies(sciFi);
                break;
            default:
                getMovies(action);
        }
        year2.setVisibility(View.INVISIBLE);
        year3.setVisibility(View.INVISIBLE);
        year1.setText("Start");
    }

    private void getMovies(List<String> movieId){
        String url = "https://www.omdbapi.com/?I=";
        JsonObjectRequest jsonObjectRequest;
        for (String id : movieId){
            url = "https://www.omdbapi.com/?i=" + id + "&apikey=" + MainActivity.API_KEY;

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        movies.add(response);
                        //String posterUrl = response.getString("Poster");
                        //Picasso.get().load(posterUrl).into(posterView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("MoviesAppError", "Failed to get data.");
                }
            });
            queue.add(jsonObjectRequest);
        }
    }

    private void setQuestion(JSONObject movie){
        try {
            String posterUrl = movie.getString("Poster");
            Picasso.get().load(posterUrl).placeholder(R.mipmap.ic_launcher).into(posterView);
            Log.d("sub",posterUrl);
            correctYear = Integer.parseInt(movie.getString("Year"));
            List<Integer> randomYear = yearGen();
            year1.setText(randomYear.get(0)+"");
            year2.setText(randomYear.get(1)+"");
            year3.setText(randomYear.get(2)+"");
            currentMovie += 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onClick(View view){
        Button temp = findViewById(view.getId());
        if(!startFlag){
            year2.setVisibility(View.VISIBLE);
            year3.setVisibility(View.VISIBLE);
            setQuestion(movies.get(currentMovie));
            startFlag = true;
        }
        else{
            if (currentMovie == movies.size()){
                if(temp.getText().equals(correctYear+"")){
                    points += 1;
                    toast.makeText(getApplicationContext(),"Correct", Toast.LENGTH_SHORT).show();
                }
                else toast.makeText(getApplicationContext(),"Nope", Toast.LENGTH_SHORT).show();
                toast.makeText(getApplicationContext(),"Final Scores: "+points, Toast.LENGTH_SHORT).show();
                highScores.edit().putInt(userName, highScores.getInt(userName, 0)+points).apply();
                Intent intent = new Intent(Game.this,
                        MainActivity.class);
                startActivity(intent);
            }
            else {
                if(temp.getText().equals(correctYear+"")){
                    points += 1;
                    toast.makeText(getApplicationContext(),"Correct", Toast.LENGTH_SHORT).show();
                }
                else toast.makeText(getApplicationContext(),"Nope", Toast.LENGTH_SHORT).show();
                setQuestion(movies.get(currentMovie));
            }
        }
    }

    private List<Integer> yearGen(){
        Random random = new Random();
        int operator;
        int correctAnswer = random.nextInt(3);
        List<Integer> temp = new ArrayList<>();
        temp.add(1);
        temp.add(1);
        temp.add(1);
        temp.set(correctAnswer, correctYear);
        int randomNum = 0;
        while(temp.get(0)== temp.get(1)||temp.get(0)== temp.get(2)||temp.get(1)== temp.get(2)){
            for(int i = 0; i < 3; i++){
                if(i!=correctAnswer){
                    randomNum = random.nextInt(10);
                    while(randomNum==0){
                        randomNum = random.nextInt(10);
                    }
                    operator = random.nextInt(2);
                    if(operator==1){
                        temp.set(i, correctYear + randomNum);
                        while(randomNum+correctYear > 2022 || randomNum == 0){
                            randomNum = random.nextInt(10);
                            temp.set(i, correctYear + randomNum);
                        }
                    }
                    else{
                        temp.set(i, correctYear - randomNum);
                    }
                }
            }
        }
        return temp;
    }
}