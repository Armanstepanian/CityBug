package org.citybugs.citybugs_new.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.citybugs.citybugs_new.R;


import org.citybugs.citybugs_new.MapsActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    Button btn,loginBtn,signBtn;
    EditText passwordField,emailField;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    String email= "",password ="";

    RequestQueue MyRequestQueue ;
    String url = "https://bugs.city/api/v2/oauth/token";
    public static PlaceholderFragment newInstance(int index) {

        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    private void sendRequest(){

        MyRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsn = new JSONObject(response);
                    editor.putString("token_type", String.valueOf(jsn.get("token_type")));
                    editor.putString("access_token", String.valueOf(jsn.get("access_token")));
                    editor.commit();

                    jsn.get("token_type");
                    jsn.get("expires_in");
                    jsn.get("access_token");
                    jsn.get("refresh_token");
                    Intent intent = new Intent(getContext(), MapsActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onErrorResponse(VolleyError error) {
                passwordField.setText("");
                passwordField.setBackgroundColor(Color.RED);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("grant_type", "social");
                MyData.put("network", "loginSimple");
                MyData.put("client_id", "1");
                MyData.put("client_secret", "eRmegC1j3fX4gr7ZoZpC9401M2OKPsM53P9XkV7a");
                MyData.put("email", email);
                MyData.put("password", password);
                return MyData;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept","application/json");

                return params;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        sharedPreferences = getContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
            View root = inflater.inflate(R.layout.fragment_main, container, false);
            View root2 = inflater.inflate(R.layout.fragment_main2, container, false);
        btn = (Button) root.findViewById(R.id.fcbButton);
        emailField = (EditText) root.findViewById(R.id.editText);
        passwordField = (EditText) root.findViewById(R.id.pass);
        loginBtn = (Button) root.findViewById(R.id.login);
        emailField.setText(sharedPreferences.getString("token_type","123"));
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailField.getText().toString();
                password = passwordField.getText().toString();
                sendRequest();
            }
        });
        if(pageViewModel.getIndex()==1)
            return root;

        else
            return root2;
    }
}