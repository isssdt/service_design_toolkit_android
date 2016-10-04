package poc.servicedesigntoolkit.getpost;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button post;
    TextView textView;
    EditText username;

    //private static final String REGISTER_URL1 = "http://54.169.59.1:9090/service_design_toolkit-web/api/refresh_current_location";
    private static final String CURRENT_LOCATION_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/refresh_current_location";
    private String jsonResponse;
    private String user;
    private String TAG = "Error";

    private static final String TAG_CURRENTLATITUDE = "currentLatitude";
    private static final String TAG_CURRENTLONGITUDE = "currentLongitude";
    private static final String TAG_SDTUSERDTO = "sdtUserDTO";
    private static final String TAG_USERNAME = "username";

    JourneyListModel jlModel;
    ArrayList<JourneyListModel> journeyList;
    Intent journeyintent;
    Timer timer;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        post = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);
        username = (EditText) findViewById(R.id.username);
        journeyList = new ArrayList<JourneyListModel>();
        journeyintent  = new Intent(MainActivity.this,JourneyList.class);

        Log.d("onCreate", "onCreate");

        post.setOnClickListener(this);
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onClick(View v) {
        user = username.getText().toString();

        if (v == post && !user.isEmpty()) {

            post();
            currentLocation();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your name ", Toast.LENGTH_SHORT).show();

        }

    }

    private void post() {
         final Intent journeyintent=new Intent(MainActivity.this,JourneyList.class);
        journeyintent.putExtra("Username",user);
        startActivity(journeyintent);
    }
    private void currentLocation(){

        final JSONObject request = new JSONObject();
        //journeyList = new ArrayList<JourneyListModel>();
        try {

            request.put(TAG_CURRENTLATITUDE, "20.02");
            request.put(TAG_CURRENTLONGITUDE, "40.30");
            JSONObject username = new JSONObject();
            request.put(TAG_SDTUSERDTO,username);
            username.put(TAG_USERNAME,user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Request",request.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                CURRENT_LOCATION_URL,request,new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response",response.toString());
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


}