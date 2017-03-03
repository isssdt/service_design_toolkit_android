package journey.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.api.APIGetJourneyListForRegister;
import common.constants.APIUrl;
import common.constants.ConstantValues;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.view.JourneyListView;
import journeyVisualization.Journey_Visualization;
import journeyemotion.emotionMeter;
import poc.servicedesigntoolkit.getpost.AppController;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.journey.view.Journey_model;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

public class JourneyListActivity extends AppCompatActivity implements LocationListener {

    private static final String JOURNEYLIST_URL = APIUrl.API_GET_JOURNEY_LIST_FOR_REGISTER;
    private static final String REGISTER_URL = APIUrl.API_REGISTER_FIELD_RESEARCHER_WITH_JOURNEY;
    private LocationManager locationManager;
    private static final int share_location_request_code = 2;
    //ListView listView;
    String Username;
    List<Journey_model> touchpointData;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    Button signUp;
    String seljourney,JourneyName;
    String provider;
    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_recycle);

        Bundle extras = getIntent().getExtras();
        new APIGetJourneyListForRegister(((JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO)).getFieldResearcherDTO().getSdtUserDTO(), new JourneyListView(this)).execute();


        touchpointData = new ArrayList<Journey_model>();
        format = new SimpleDateFormat("dd MMM yyyy");
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);
    }

    public void ButtonAction(String button,String Journey,Date startDate,Date endDate){
        JourneyName = Journey ;
        Log.d("Back", button + Journey+startDate+endDate+"");
        if(button.equals("View")){
            Intent i = new Intent(JourneyListActivity.this,emotionMeter.class);
            i.putExtra("JourneyName", JourneyName);
            i.putExtra("Username", Username);
            startActivity(i);

        }else if(button.equals("Sign up")){
            Log.d("Inside","yeah");
            final AlertDialog.Builder adb = new AlertDialog.Builder(JourneyListActivity.this);
            adb.setTitle("Register");
            adb.setMessage("Journey Name : " + JourneyName+"\n"+
                    "Date : "+format.format(startDate)+" - "+format.format(endDate));
            adb.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (ContextCompat.checkSelfPermission(JourneyListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(JourneyListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, share_location_request_code);
                    } else {
                        Location location = locationManager.getLastKnownLocation(provider);
                        if (location != null) {
                            System.out.println("Provider " + provider + " has been selected.");
                            onLocationChanged(location);
                        } else {
                            Toast.makeText(getApplicationContext(),"Location not available",Toast.LENGTH_SHORT );
                        }
                        registeruser(JourneyName);
                    }
                }
            });
            adb.setNegativeButton("Cancel", null);
            adb.show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (int) (location.getLatitude());
        double lng = (int) (location.getLongitude());
        Log.d("location","lat : "+lat + " lon : "+lng);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case share_location_request_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    registeruser(JourneyName);

                } else {
                    Toast.makeText(JourneyListActivity.this, "Permission denied to get your LOCATION", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void registeruser(String journey) {
        final JSONObject request = new JSONObject();
        try {
            final JSONObject journeyDTO = new JSONObject();

            request.put("journeyDTO", journeyDTO);
            journeyDTO.put("journeyName", journey);

            JSONObject fieldResearcherDTO = new JSONObject();
            request.put("fieldResearcherDTO", fieldResearcherDTO);

            JSONObject sdtUserDTO = new JSONObject();
            fieldResearcherDTO.put("sdtUserDTO", sdtUserDTO);

            sdtUserDTO.put("username", Username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest registerJourney = new JsonObjectRequest(Request.Method.PUT,
                REGISTER_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent i = new Intent(JourneyListActivity.this, Journey_Visualization.class);
                JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
                journeyFieldResearcherDTO.setJourneyDTO(new JourneyDTO());
                journeyFieldResearcherDTO.getJourneyDTO().setJourneyName(seljourney);
                journeyFieldResearcherDTO.setFieldResearcherDTO(new FieldResearcherDTO());
                journeyFieldResearcherDTO.getFieldResearcherDTO().setSdtUserDTO(new SdtUserDTO());
                journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().setUsername(Username);
                i.putExtra(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
                i.putExtra("JourneyName", seljourney);
                i.putExtra("Username", Username);
                startActivity(i);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(registerJourney);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}
