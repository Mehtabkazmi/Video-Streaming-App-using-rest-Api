package com.example.javaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    public JsonToPojo jsonToPojo = new JsonToPojo();
    RecyclerView videoList;
    VideoAdapter adapter;
    List<Video> all_videos;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar=findViewById(R.id.home_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        all_videos = new ArrayList<>();

        videoList = findViewById(R.id.videoList);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this, all_videos);
        videoList.setAdapter(adapter);
        getJsonData();

    }

    private void getJsonData() {
        String URL = "http://10.0.2.2:8080/api/videos";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.d(TAG, "onResponse: "+ response);
                //                    JSONArray categories = response.getJSONArray("categories");
//                    JSONObject categoriesData = categories.getJSONObject(0);
//                    JSONArray videos = response.getJSONArray("videos");

                ObjectMapper objectMapper = new ObjectMapper();
                Video[] videos = null;
                try {
                    videos = objectMapper.readValue(jsonToPojo.convertJsonArrayToArray(response), Video[].class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "onResponse: " + videos);

                for (int i = 0; i < videos.length; i++) {
//                    Video video = videos[i];
//                    Video v = new Video();

//                        v.setTitle(video.getString("title"));
//                        v.setDescription(video.getString("description"));
//                        v.setAuthor(video.getString("subtitle"));
//                        v.setImageUrl(video.getString("thumb"));
//                        JSONArray videoUrl = video.getJSONArray("sources");
//                        v.setVideoUrl(videoUrl.getString(0));

                    all_videos.add(videos[i]);
                    adapter.notifyDataSetChanged();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error Response: " + error.getMessage());
            }
        });

        requestQueue.add(objectRequest);
    }
}