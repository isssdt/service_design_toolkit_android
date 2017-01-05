package journey.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.constants.ConstantValues;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;
import poc.servicedesigntoolkit.getpost.AppController;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.Touchpoint.RecyclerTouchListener;
import poc.servicedesigntoolkit.getpost.journey.view.JourneyDTO;
import poc.servicedesigntoolkit.getpost.journey.view.Journey_model;
import poc.servicedesigntoolkit.getpost.journey.view.Journey_recycle_adapter;
import touchpoint.activity.TouchPointListActivity;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

public class JourneyListActivity extends AppCompatActivity {

    private static final String JOURNEYLIST_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_journey_list_for_register";
    private static final String REGISTER_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/register_field_researcher_with_journey";
    private static final String TAG_JOURNEYLIST = "journeyDTOList";
    private static final String TAG_JOURNEYNAME = "journeyName";
    //ListView listView;
    String Username;
    List<Journey_model> touchpointData;
    ArrayList<Journey_model> journeyList;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    Button signUp;
    ArrayAdapter journeyAdapter;
    String seljourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_recycle);
        Bundle extras = getIntent().getExtras();
//        Username = (String) extras.get("Username");
        Username = ((JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO)).getFieldResearcherDTO().getSdtUserDTO().getUsername();

        signUp = (Button) findViewById(R.id.signup);

        recyclerView = (RecyclerView) findViewById(R.id.journeyrecycle);
        recyclerView.setHasFixedSize(true);

        touchpointData = new ArrayList<Journey_model>();
/*

        listView = (ListView) findViewById(R.id.listView);

        journeyList = new ArrayList<Journey_model>();
*/

        new HttpRequestTask().execute();
        recyclerView.setAdapter(recyclerViewadapter);
        //listView.setAdapter(journeyAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Journey_model model =touchpointData.get(position);

                //if(view.getId() == signUp.getId()){
                //    registeruser(model.getJourneyName());
                //}else{
                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            JourneyListActivity.this);
                    adb.setTitle("Register");
                    adb.setMessage(" Journey Name : " + model.getJourneyName()+"\n"+
                                    "Start Date : "+model.getStartDate()+"\n"+
                                    "End Date : "+model.getEndDate());
                    adb.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            registeruser(model.getJourneyName());
                        }
                    });
                    adb.setNegativeButton("Cancel", null);
                    adb.show();

                //}

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final String journey = (String) listView.getItemAtPosition(position);
                Log.d("PRESSED", journey);
//                Toast.makeText(getApplicationContext(), "Registered for : " + journey+" poc.servicedesigntoolkit.getpost.journey.", Toast.LENGTH_SHORT).show();


                AlertDialog.Builder adb = new AlertDialog.Builder(
                        JourneyListActivity.this);
                adb.setTitle("Register");
                adb.setMessage(" Journey Name "
                        +parent.getItemAtPosition(position));
                adb.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        registeruser(journey);
                    }
                });
                adb.setNegativeButton("Cancel", null);
                adb.show();

                //registeruser(journey);

            }
        });*/




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
                Intent i = new Intent(JourneyListActivity.this, TouchPointListActivity.class);
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
                        // error
                        Log.d("Error.Response", error.toString());
                        AlertDialog.Builder adb = new AlertDialog.Builder(JourneyList.this);
                        adb.setTitle("Register");
                        adb.setMessage(" You have Already Completed this Journey ");
                        adb.setPositiveButton("Ok", null);
                        adb.show();
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

                JourneyListDTO journeyListDTO = restTemplate.getForObject(JOURNEYLIST_URL, JourneyListDTO.class);
                for (JourneyDTO journeyDTO : journeyListDTO.getJourneyDTOList()) {
                    Log.d("Journey Name ",journeyDTO.getJourneyName());
                    Log.d("Start Date ",journeyDTO.getStartDate().toString());
                    Log.d("End Date ",journeyDTO.getEndDate().toString());
                    Journey_model journey_model = new Journey_model(journeyDTO.getJourneyName(),journeyDTO.getStartDate(),journeyDTO.getEndDate());
                    journey_model.setJourneyName(journeyDTO.getJourneyName());
                    journey_model.setStartDate(journeyDTO.getStartDate());
                    journey_model.setEndDate(journeyDTO.getEndDate());
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
}
