package poc.servicedesigntoolkit.getpost;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointList_Model;
import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointMain;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button register;
    TextView textView;
    EditText username;

    private static final String CURRENT_LOCATION_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/field_researcher_register";
    private String user;
    private static final String TAG_USERNAME = "username";
    private static final String TAG_REGISTER = "This Field Researcher already registered with a Journey";
    private static final String TAG_NOTREGISTER = "This Field Researcher has not registered with any Journey";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (Button) findViewById(R.id.researchList);
        textView = (TextView) findViewById(R.id.textView);
        username = (EditText) findViewById(R.id.username);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        user = username.getText().toString();
        if (v == register && !user.isEmpty()) {
            currentLocation();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your name ", Toast.LENGTH_SHORT).show();
        }
    }

    private void currentLocation() {
        final JSONObject request = new JSONObject();
        try {
            request.put(TAG_USERNAME,user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Request", request.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                CURRENT_LOCATION_URL, request, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", " > " + response.toString());

                JSONObject jsonObj;
                Log.d("JOURNEYresponse", response.toString());
                if (response != null) {
                    try {
                        jsonObj = new JSONObject(response.toString());
                        String Response = jsonObj.getString("message");

                        if(TAG_NOTREGISTER.equals(Response)){
                            final Intent journeyintent = new Intent(MainActivity.this, JourneyList.class);
                            journeyintent.putExtra("Username", user);
                            startActivity(journeyintent);
                        }else if(TAG_REGISTER.equals(Response)){
                            final Intent journeyintent = new Intent(MainActivity.this, TouchpointMain.class);
                            journeyintent.putExtra("Username", user);
                            startActivity(journeyintent);
                        }
                        Log.d("MESSAGE",jsonObj.getString("message"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

        post();
    }


    private void post() {
        //if(){
        Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_SHORT);
            /*final Intent journeyintent = new Intent(MainActivity.this, JourneyList.class);
            journeyintent.putExtra("Username", user);
            startActivity(journeyintent);*/
       /* }else if(){
            final Intent journeyintent = new Intent(MainActivity.this, TouchpointMain.class);
            journeyintent.putExtra("Username", user);
            startActivity(journeyintent);
        }*/
    }
}