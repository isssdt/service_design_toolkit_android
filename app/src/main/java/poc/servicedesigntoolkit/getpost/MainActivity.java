package poc.servicedesigntoolkit.getpost;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Button post;
    TextView textView;
    EditText username;

    private static final String CURRENT_LOCATION_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/refresh_current_location";
    private String jsonResponse;
    private String user;
    private String TAG = "Error";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private static final String TAG_CURRENTLATITUDE = "currentLatitude";
    private static final String TAG_CURRENTLONGITUDE = "currentLongitude";
    private static final String TAG_SDTUSERDTO = "sdtUserDTO";
    private static final String TAG_USERNAME = "username";

    ArrayList<JourneyListModel> journeyList;
    Intent journeyintent;

    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Double lat = 0.0;
    Double lon = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        post = (Button) findViewById(R.id.researchList);
        textView = (TextView) findViewById(R.id.textView);
        username = (EditText) findViewById(R.id.username);
        journeyList = new ArrayList<JourneyListModel>();
        journeyintent = new Intent(MainActivity.this, JourneyList.class);

        Log.d("onCreate", "onCreate");

        post.setOnClickListener(this);
        // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onClick(View v) {
        user = username.getText().toString();
        if (v == post && !user.isEmpty()) {
            post();
            buildGoogleApiClient();
            checkLocationPermission();
            currentLocation();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your name ", Toast.LENGTH_SHORT).show();
        }
    }

    private void post() {
        final Intent journeyintent = new Intent(MainActivity.this, JourneyList.class);
        journeyintent.putExtra("Username", user);
        startActivity(journeyintent);
    }

    private void currentLocation() {
        Log.d("currentLocation", "currentLocation");
        final JSONObject request = new JSONObject();
        try {

            request.put(TAG_CURRENTLATITUDE, lat);
            request.put(TAG_CURRENTLONGITUDE, lon);
            JSONObject username = new JSONObject();
            request.put(TAG_SDTUSERDTO, username);
            username.put(TAG_USERNAME, user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Request", request.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                CURRENT_LOCATION_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        //update
        currentlocationUpdate(location);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    private void currentlocationUpdate(Location location) {
        Log.d("currentLocation2", "currentLocation2");
        final JSONObject request = new JSONObject();
        //journeyList = new ArrayList<JourneyListModel>();
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            request.put(TAG_CURRENTLATITUDE, location.getLatitude());
            request.put(TAG_CURRENTLONGITUDE, location.getLongitude());

            JSONObject username = new JSONObject();
            request.put(TAG_SDTUSERDTO, username);

            username.put(TAG_USERNAME, "Gunjan");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Requestcurrentloc", request.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                CURRENT_LOCATION_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
            Log.d("lat",lat.toString());
            Log.d("lon",lon.toString());
        }
        Log.d("onConnected","onConnected");


        //currentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}