package poc.servicedesigntoolkit.getpost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import poc.servicedesigntoolkit.getpost.SessionManagement.SessionManager;
import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointMain;

public class JourneyList extends AppCompatActivity {

    ListView listView;
    private static final String JOURNEYLIST_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_journey_list_for_register";
    private static final String REGISTER_URL="http://54.169.59.1:9090/service_design_toolkit-web/api/register_field_researcher_with_journey";

    private static final String TAG_JOURNEYLIST = "journeyDTOList";
    private static final String TAG_JOURNEYNAME = "journeyName";

    String Username;
    ArrayList<String> journeyList;

    ArrayAdapter journeyAdapter;
    String seljourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);
        Bundle extras = getIntent().getExtras();
        Username = (String) extras.get("Username");

        listView=(ListView)findViewById(R.id.listView);

        journeyList = new ArrayList<String>();

/*
        JSONObject request = new JSONObject();

        try {

            request.put("isActive", "Y");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        JsonObjectRequest journeyListJSON = new JsonObjectRequest(Request.Method.GET,
                JOURNEYLIST_URL, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                JSONObject jsonObj;

                if (response != null) {
                    try {
                        jsonObj = new JSONObject(response.toString());

                        // Getting JSON Array node
                        JSONArray list = jsonObj.getJSONArray(TAG_JOURNEYLIST);

                        // looping through All Contacts
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject c = list.getJSONObject(i);

                            journeyList.add(c.getString(TAG_JOURNEYNAME));

                        }
                        Log.d("JourneyListBefore" ,journeyList.toString());

                        journeyAdapter=new ArrayAdapter(JourneyList.this,android.R.layout.simple_list_item_1 ,journeyList);

                        listView.setAdapter(journeyAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                journeyAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("REQUEST",journeyListJSON.toString());
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(journeyListJSON);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String journey = (String) listView.getItemAtPosition(position);
                Log.d("PRESSED",journey);
                Toast.makeText(getApplicationContext(), "Journey Selected : "+journey, Toast.LENGTH_SHORT).show();

                registeruser(journey);

            }
        });
    }

    public void registeruser(String journey){
        seljourney = journey;
        final JSONObject request = new JSONObject();
        try {
            final JSONObject journeyDTO = new JSONObject();

            request.put("journeyDTO",journeyDTO);
            journeyDTO.put("journeyName", seljourney);

            JSONObject fieldResearcherDTO = new JSONObject();
            request.put("fieldResearcherDTO",fieldResearcherDTO);

            JSONObject sdtUserDTO = new JSONObject();
            fieldResearcherDTO.put("sdtUserDTO",sdtUserDTO);

            sdtUserDTO.put("username",Username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Request",request.toString());
        JsonObjectRequest registerJourney = new JsonObjectRequest(Request.Method.PUT,
                REGISTER_URL,request,new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response"," > "+response.toString());
                Intent i = new Intent(JourneyList.this,TouchpointMain.class);
                i.putExtra("JourneyName",seljourney);
                i.putExtra("Username",Username);
                startActivity(i);
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
        ){
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
}
