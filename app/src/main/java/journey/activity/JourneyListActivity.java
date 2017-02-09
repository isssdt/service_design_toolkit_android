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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import common.constants.APIUrl;
import common.constants.ConstantValues;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;
import journeyVisualization.Journey_Visualization;
import journeyemotion.emotionMeter;
import poc.servicedesigntoolkit.getpost.AppController;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.Touchpoint.RecyclerTouchListener;
import journey.dto.JourneyDTO;
import poc.servicedesigntoolkit.getpost.journey.view.Journey_model;
import poc.servicedesigntoolkit.getpost.journey.view.Journey_recycle_adapter;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

public class JourneyListActivity extends AppCompatActivity implements LocationListener {

    private static final String JOURNEYLIST_URL = APIUrl.API_GET_JOURNEY_LIST_FOR_REGISTER;
    private static final String REGISTER_URL = APIUrl.API_REGISTER_FIELD_RESEARCHER_WITH_JOURNEY;
    private static final String TAG_JOURNEYLIST = "journeyDTOList";
    private static final String TAG_JOURNEYNAME = "journeyName";
    private LocationManager locationManager;
    private static final int share_location_request_code = 2;
    //ListView listView;
    String Username;
    List<Journey_model> touchpointData;
    ArrayList<Journey_model> journeyList;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    Button signUp;
    ArrayAdapter journeyAdapter;
    String seljourney,JourneyName;
    SimpleDateFormat format;
    Date startDate;
    Date endDate;
    String start;
    String end,provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_recycle);

        Bundle extras = getIntent().getExtras();
        Username = ((JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO)).getFieldResearcherDTO().getSdtUserDTO().getUsername();

        signUp = (Button) findViewById(R.id.signup);

        recyclerView = (RecyclerView) findViewById(R.id.journeyrecycle);
        recyclerView.setHasFixedSize(true);

        touchpointData = new ArrayList<Journey_model>();

        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);


        new HttpRequestTask().execute();
        recyclerView.setAdapter(recyclerViewadapter);
        //listView.setAdapter(journeyAdapter);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("try","try" );
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                final Journey_model model =touchpointData.get(position);

                startDate = model.getStartDate();
                endDate = model.getEndDate();
                format = new SimpleDateFormat("dd MMM yyyy");
                start = format.format(startDate);
                end = format.format(endDate);
                JourneyName = model.getJourneyName();

                if("DONE".equals(model.getCompleted())){
                    Intent i = new Intent(JourneyListActivity.this,emotionMeter.class);
                    i.putExtra("JourneyName", JourneyName);
                    i.putExtra("Username", Username);
                    startActivity(i);
                }else{
                    final AlertDialog.Builder adb = new AlertDialog.Builder(JourneyListActivity.this);
                    adb.setTitle("Register");
                    adb.setMessage("Journey Name : " + JourneyName+"\n"+
                            "Date : "+start+" - "+end);
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
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (int) (location.getLatitude());
        double lng = (int) (location.getLongitude());
        Toast.makeText(this, "Lat : "+lat + " lon : " +lng , Toast.LENGTH_SHORT).show();
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
        seljourney = journey;
        final JSONObject request = new JSONObject();
        try {
            final JSONObject journeyDTO = new JSONObject();

            request.put("journeyDTO", journeyDTO);
            journeyDTO.put("journeyName", seljourney);

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

    private class HttpRequestTask extends AsyncTask<Void, Void, JourneyListDTO> {
        @Override
        protected JourneyListDTO doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(Username);

                JourneyListDTO journeyListDTO = restTemplate.postForObject(JOURNEYLIST_URL, sdtUserDTO,JourneyListDTO.class);
                for (JourneyDTO journeyDTO : journeyListDTO.getJourneyDTOList()) {
                    Journey_model journey_model = new Journey_model(journeyDTO.getJourneyName(),journeyDTO.getStartDate(),journeyDTO.getEndDate());
                    journey_model.setJourneyName(journeyDTO.getJourneyName());
                    journey_model.setStartDate(journeyDTO.getStartDate());
                    journey_model.setEndDate(journeyDTO.getEndDate());
                    if(journeyDTO.getJourneyFieldResearcherListDTO()!= null){
                        List<JourneyFieldResearcherDTO> frList= journeyDTO.getJourneyFieldResearcherListDTO().getJourneyFieldResearcherDTOList();

                        for (JourneyFieldResearcherDTO t:frList)
                        {
                                journey_model.setCompleted(t.getStatus());
                                Log.d("t.getStatus",t.getStatus());
                        }
                   }
                    touchpointData.add(journey_model);
                }
                return journeyListDTO;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(JourneyListDTO journeyListDTO) {
            LinearLayoutManager llm = new LinearLayoutManager(JourneyListActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewadapter = new Journey_recycle_adapter(touchpointData, JourneyListActivity.this);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(recyclerViewadapter);
            recyclerViewadapter.notifyDataSetChanged();



           /* journeyAdapter = new ArrayAdapter(JourneyListActivity.this, android.R.layout.simple_list_item_1, journeyList);
            listView.setAdapter(journeyAdapter);
            journeyAdapter.notifyDataSetChanged();*/
        }

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
