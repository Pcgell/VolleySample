package com.example.lecture.volleysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String url = "";

        //final String imgUrlPattern = "https://www.kiva.org/img/720/%d.jpg";
        final VolleySingleton vs = VolleySingleton.getInstance(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArr = new JSONArray(response);
                    JSONObject job = jArr.getJSONObject(2);
                    String name = job.getString("nombre");

                    TextView tv = findViewById(R.id.textView);
                    tv.setText(name);
                    NetworkImageView niv = findViewById(R.id.imageView);
                    String img = job.getString("img");
                    niv.setImageUrl(img,vs.getImageLoader());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });


        vs.getRequestQueue().add(stringRequest);
    }
}
