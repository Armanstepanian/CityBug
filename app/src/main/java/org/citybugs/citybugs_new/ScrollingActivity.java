package org.citybugs.citybugs_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.citybugs.citybugs_new.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScrollingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Model> modelArrayList = new ArrayList<>();
    Controller adapter;


    RequestQueue MyRequestQueue;
    String url = "https://bugs.city/api/v2/bugs/paginate?locale=en";
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    String acess_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new Controller(this, modelArrayList);
        recyclerView.setAdapter(adapter);

        sharedPreferences = getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        acess_token = sharedPreferences.getString("access_token", "");


        MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsnArr = new JSONObject(response);

                    JSONObject data = jsnArr.getJSONObject("data");

                    JSONArray bugs = data.getJSONArray("bugs");
                    for (int i = 0; i < bugs.length(); i++) {
                        System.out.println(bugs.get(i));
                        JSONObject jsn =  bugs.getJSONObject(i);
                        JSONObject category = jsn.getJSONObject("author");
                        addOnRecyclerView(category.getString("full_name"),jsn.getString("created_at"),jsn.getString("address") + jsn.getString("comment"),
                                jsn.getString("image_url"),category.getString("image_url"),
                                jsn.getInt("likes_count"),i,jsn.getString("id"));

                        System.out.println(jsn.getInt("id"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(954);

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("locale", "en");

                return MyData;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer " + acess_token);
                header.put("Accept", "application/json");

                return header;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }


    private void addOnRecyclerView(String name, String titl, String context, String imageUrl, String profilePickURL, int likeCount, int commCount,String id) {
        Model model = new Model(name, titl, profilePickURL, imageUrl, context, likeCount, commCount, 1,id);

        modelArrayList.add(model);
        adapter.notifyDataSetChanged();
    }

}
