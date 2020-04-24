package com.example.tutorialspoint7.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class home extends AppCompatActivity {
    TextView mTextViewResult;
    ImageView imageView;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageView = findViewById(R.id.imageView2);
        mTextViewResult = findViewById(R.id.textView5);
        mQueue = Volley.newRequestQueue(this);

        jsonParse();

    }
    private void jsonParse() { String url = "https://www.omdbapi.com/?i=tt3896198&apikey=fe4f1194";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray weather = null;
                        String hold = null;
                        String city = null;
                        String clouds = null;
                        String poster = null;

                        try {
                           // weather = response.getJSONArray("Ratings");
                           // for (int i = 0; i < weather.length(); i++)
                          //  {
                          //      hold = weather.getJSONObject(i).getString("Source");
                          // }
                            //clouds = response.getJSONObject("Ratings").getString("Source");
                            city = response.getString("Title");
                            poster = response.getString("Poster");
                            //Picasso allows for hassle-free image loading in your application—often in one line of code!
                            Picasso.get().load(poster).into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       // mTextViewResult.setText("Weather in: " + city + "\n" + "Cloudiness: " + clouds + "\n" + "Pressure: " + pressure + "\n" + "Humidity: " + humidity );
                        mTextViewResult.setText("Weather in: " + poster);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
