package com.example.lecture.volleysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String url = "https://api.kivaws.org/v1/loans/newest.json";

        final String imgUrlPattern = "https://www.kiva.org/img/720/%d.jpg";
        final VolleySingleton vs = VolleySingleton.getInstance(this);

        JsonObjectRequest jor = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray loans = response.getJSONArray("loans");

                    JSONObject loan = loans.getJSONObject(0);
                    String name = loan.getString("name");
                    TextView titulo =(TextView) findViewById(R.id.textView);
                    titulo.setText(name);

                    int id = loan.getJSONObject("image").getInt("id");

                    final NetworkImageView imageView = findViewById(R.id.imageView);
                    String urlImage = String.format(imgUrlPattern,id);

                    imageView.setImageUrl(urlImage,vs.getImageLoader());
//
//                     vs.getImageLoader().get(urlImage, new ImageLoader.ImageListener() {
//                         @Override
//                         public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                             imageView.setImageBitmap(response.getBitmap());
//                         }
//
//                         @Override
//                         public void onErrorResponse(VolleyError error) {
//
//                         }
//                     });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        vs.getRequestQueue().add(jor);
    }
}
