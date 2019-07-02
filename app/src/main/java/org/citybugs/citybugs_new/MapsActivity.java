package org.citybugs.citybugs_new;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import org.citybugs.citybugs_new.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kinda.alert.KAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    RequestQueue MyRequestQueue;
    LatLng target;
    int height, width;
    int distance = 400;
    String url;
    //= "https://bugs.city/api/v2/bugs?locale=en&latitude=" +
    //        latitode + "&longitude="+langitude + "&distance="+distance;
    Marker marker;
    Drawable drw = null;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    String acess_token;
    Dialog myDialog;
    String created_at;
    String image_url, address;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sharedPreferences = getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        acess_token = sharedPreferences.getString("access_token", "");
        Toast.makeText(getApplicationContext(), acess_token, Toast.LENGTH_LONG);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        distance = (int) Math.sqrt(height * height + width * width);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        MarkerOptions markerOptions = new MarkerOptions().position(sydney);
        marker = googleMap.addMarker(markerOptions);

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                target = mMap.getCameraPosition().target;
                marker.setPosition(target);
            }

        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }

        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                url =
                 "https://bugs.city/api/v2/bugs?locale=en&latitude=" +
                       getLatitode() + "&longitude="+getLangitude() + "&distance="+getDistance();
                sendrequest(url);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.navigation_metka:

             /*   setMarkerAllert("44444",Uri.parse("https://scontent.fevn1-3.fna.fbcdn.net/v/t1.15752-9/6473319" +
                        "1_424975471566883_3404036339727859712_n.jpg?_nc_cat=111&_nc_ht=scontent.fevn1-3.fna&oh=621f4beb5b50df6ea4770bbaba583bf6&oe=5D893DE3"),4);

*/
                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                startActivity(intent);
                break;

            case R.id.dzaxItem:

                Intent intent1 = new Intent(getApplicationContext(), ScrollingActivity.class);
                startActivity(intent1);
                break;

            case R.id.navigation_notifications:

                break;

            case R.id.navigation_plus:
                Intent intent3 = new Intent(getApplicationContext(), ScrollingActivity.class);
                startActivity(intent3);
                break;

        }

        return true;
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void sendrequest(String url) {

        MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mMap.clear();
                try {
                    JSONObject json = new JSONObject(response);

                    System.out.println(getDistance());

                    JSONArray jsn = json.getJSONArray("bugs");
                    for(int i = 0 ; i < jsn.length() ; i ++){

                        JSONObject jsonObject = jsn.getJSONObject(i);
                        JSONObject status = jsonObject.getJSONObject("status");

                        LatLng markOpt = new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
                        MarkerOptions markerOptions = new MarkerOptions().position(markOpt);
                        marker = mMap.addMarker(markerOptions);
                        marker.setTitle(jsonObject.getString("address"));
                        marker.setTag(jsonObject.get("comment"));

                        switch (status.getString("status_color")){
                            case "yellow":
                                marker.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.mipmap.yellow));
                             break;

                            case "red":
                                marker.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.mipmap.red));
                             break;

                            case "green":
                                marker.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.mipmap.green));
                             break;
                        }

                         address = jsonObject.getString("address");
                         image_url = jsonObject.getString("image_url");
                         created_at = jsonObject.getString("created_at");
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                try {
                                    myCustomDialog(address,image_url,created_at);
                                }catch (Exception e){}
                            //    setMarkerAllert(marker.getTag().toString(), 1);
                                return false;
                            }
                        });

                    }
                    System.err.println(response);
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
                MyData.put("locale", "en");
                MyData.put("latitude", String.valueOf(getLatitode()));
                MyData.put("longitude", String.valueOf(getLangitude()));
                MyData.put("distance", String.valueOf(getDistance()));
                return MyData;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + acess_token);

                return params;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }


    public double getDistance() {
        LatLngBounds curScreen = mMap.getProjection()
                .getVisibleRegion().latLngBounds;

        double x1= curScreen.northeast.latitude;
        double x2 = curScreen.southwest.latitude;

        double y1 = curScreen.northeast.longitude;
        double y2 = curScreen.southwest.longitude;

        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2 - y1, 2)) * 100000000;
    }

    public double getLangitude(){

        LatLngBounds curScreen = mMap.getProjection()
                .getVisibleRegion().latLngBounds;

        return curScreen.getCenter().latitude;
    }

    public double getLatitode()
    {

        LatLngBounds curScreen = mMap.getProjection()
                .getVisibleRegion().latLngBounds;

        double longitude = curScreen.getCenter().longitude;

        return longitude;
    }
    public void myCustomDialog(String title,String url,String text){
        ImageView img;
        TextView txt;
        RequestManager glide ;

        glide = Glide.with(getApplicationContext());
        myDialog = new Dialog(MapsActivity.this);
        myDialog.setContentView(R.layout.custom_allert);
        myDialog.setTitle(title);
        img = (ImageView) myDialog.findViewById(R.id.imageView2);
        txt = (TextView)myDialog.findViewById(R.id.textView);
        txt.setText(text);
        glide.load(url).into(img);

        myDialog.show();

    }

    public void setMarkerAllert(String title, int id) {

        try {
            KAlertDialog pDialog = new KAlertDialog(this, KAlertDialog.CUSTOM_IMAGE_TYPE);
            pDialog.setTitleText(String.valueOf(Html.fromHtml(title)));
            pDialog.setCustomImage(R.drawable.ic_launcher);
            pDialog.show();
        } catch (Exception ex) {
        }
        new AlertDialog.Builder(getApplicationContext()).setMessage(Html.fromHtml(title)).setIcon(drw).setTitle(Html.fromHtml(title));

    }
}
