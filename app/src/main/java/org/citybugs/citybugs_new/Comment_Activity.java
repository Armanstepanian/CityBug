package org.citybugs.citybugs_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comment_Activity extends AppCompatActivity {
    RecyclerView recycler;
    ArrayList<Comments_Model> modelArrayList = new ArrayList<>();
    Comments_Controller adapter;

    String id ="";
    RequestQueue MyRequestQueue;
    String url = "https://bugs.city/api/v2/comments/get/";

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    String acess_token;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        setContentView(R.layout.comments);
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getString("id");

        recycler = (RecyclerView)findViewById(R.id.comm_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);


        sharedPreferences = getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        acess_token = sharedPreferences.getString("access_token", "");

        adapter = new Comments_Controller(getApplicationContext(), modelArrayList);
        recycler.setAdapter(adapter);

        MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsnArr = new JSONObject(response);

                    JSONArray bugs = jsnArr.getJSONArray("comments");
                    for (int i = 0; i < bugs.length(); i++) {

                        JSONObject jsn =  bugs.getJSONObject(i);
                        JSONObject category = jsn.getJSONObject("author");
                        addOnRecyclerView(category.getString("full_name"),
                                jsn.getString("comment"),jsn.getString("created_at"),
                                jsn.getString("image_url"),category.getString("image_url"));


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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Accept", "application/json");
                header.put("Authorization", "Bearer " + acess_token);


                return header;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }


    private void addOnRecyclerView(String name, String titl, String context, String imageUrl,String com_user_img) {
        Comments_Model model = new Comments_Model(name,context,titl,imageUrl,com_user_img);
        modelArrayList.add(model);
        adapter.notifyDataSetChanged();
    }
    }

