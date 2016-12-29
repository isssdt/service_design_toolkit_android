package poc.servicedesigntoolkit.getpost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.api.APIDataHandler;
import journey.controller.JourneyController;
import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointMain;

public class JourneyList extends AppCompatActivity implements APIDataHandler {

    private static final String JOURNEYLIST_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_journey_list_for_register";
    private static final String REGISTER_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/register_field_researcher_with_journey";
    private static final String TAG_JOURNEYLIST = "journeyDTOList";
    private static final String TAG_JOURNEYNAME = "journeyName";
    ListView listView;
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

        listView = (ListView) findViewById(R.id.listView);

        journeyList = new ArrayList<String>();


        new JourneyController(this).getJourneyList();



        listView.setAdapter(journeyAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String journey = (String) listView.getItemAtPosition(position);
                Log.d("PRESSED", journey);
                Toast.makeText(getApplicationContext(), "Registered for : " + journey+" journey.", Toast.LENGTH_SHORT).show();

                registeruser(journey);

            }
        });
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
                Intent i = new Intent(JourneyList.this, TouchpointMain.class);
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
    public void handleData(Object data) {
        JourneyListDTO journeyListDTO = (JourneyListDTO) data;
        for (JourneyDTO journeyDTO : journeyListDTO.getJourneyDTOList()) {
            journeyList.add(journeyDTO.getJourneyName());
        }

        journeyAdapter = new ArrayAdapter(JourneyList.this, android.R.layout.simple_list_item_1, journeyList);
        listView.setAdapter(journeyAdapter);
        journeyAdapter.notifyDataSetChanged();
    }
}
