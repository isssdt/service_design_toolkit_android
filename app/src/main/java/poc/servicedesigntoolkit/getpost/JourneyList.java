package poc.servicedesigntoolkit.getpost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
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

import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import poc.servicedesigntoolkit.getpost.SessionManagement.SessionManager;
import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointMain;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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

        new HttpRequestTask().execute();
        Log.d("FLOW","oncreate");
        Log.d("Journey LIST", journeyList.toString());

        listView.setAdapter(journeyAdapter);


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

    private class HttpRequestTask extends AsyncTask<Void, Void, JourneyListDTO> {
        @Override
        protected JourneyListDTO doInBackground(Void... params) {
            try {
                final String url = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_journey_list_for_register";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                JourneyListDTO journeyListDTO = restTemplate.getForObject(url, JourneyListDTO.class);
                for (JourneyDTO journeyDTO : journeyListDTO.getJourneyDTOList()) {
                    Log.d("Journey Name", journeyDTO.getJourneyName());
                    journeyList.add(journeyDTO.getJourneyName());
                }
                Log.d("Journey LIST", journeyList.toString());

                return journeyListDTO;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(JourneyListDTO journeyListDTO) {
            journeyAdapter=new ArrayAdapter(JourneyList.this,android.R.layout.simple_list_item_1 ,journeyList);
            listView.setAdapter(journeyAdapter);
            journeyAdapter.notifyDataSetChanged();
        }

    }
}
