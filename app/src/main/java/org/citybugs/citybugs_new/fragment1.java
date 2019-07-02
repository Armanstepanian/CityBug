package org.citybugs.citybugs_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.citybugs.citybugs_new.R;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import retrofit2.Response;


public class fragment1 extends Fragment {
    Button button ;
    RequestQueue MyRequestQueue ;
    EditText pass,email,firstName,lastName;


    String url = "https://bugs.city/api/v2/oauth/token";
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main2,container,false);
        final User user = new User("test@test.new","0506200",
                "eRmegC1j3fX4gr7ZoZpC9401M2OKPsM53P9XkV7a","1","loginSimple","social");
        button = (Button)root.findViewById(R.id.fcbButton2);
        pass = (EditText)root.findViewById(R.id.pass2);
        firstName = (EditText)root.findViewById(R.id.fisrtName);
        lastName = (EditText)root.findViewById(R.id.lastName);
        email = (EditText)root.findViewById(R.id.email2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    //   sendRequest();
       Intent intent = new Intent(getContext(),MapsActivity.class);
       startActivity(intent);
            }
        });

        return root;
    }

    public static fragment1 newInstance() {
        fragment1 fragment = new fragment1();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void sendRequest(){
        MyRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsn = new JSONObject(response);
                    jsn.get("token_type");
                    jsn.get("expires_in");
                    jsn.get("access_token");
                    jsn.get("refresh_token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("grant_type", "social");
                MyData.put("network", "registerSimple");
                MyData.put("client_id", "1");
                MyData.put("client_secret", "eRmegC1j3fX4gr7ZoZpC9401M2OKPsM53P9XkV7a");
                MyData.put("first_name",firstName.getText().toString());
                MyData.put("last_name",lastName.getText().toString());
                MyData.put("email", email.getText().toString());
                MyData.put("password",pass.getText().toString());
                return MyData;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type","application/x-www-form-urlencoded");
                return header;
            }
        };

MyRequestQueue.add(MyStringRequest);
    }
    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);

    }
}
