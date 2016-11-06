package poc.servicedesigntoolkit.getpost;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import poc.servicedesigntoolkit.getpost.Controller.TouchpointList_Controller;
import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointList_Model;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan Pathak on 04-Oct-16.
 */

public class TouchpointList extends Activity  {


    ListView listView;
    ArrayList<TouchpointList_Model> touchList;
    String JourneyName,Username;
    TouchpointList_Controller touchpoint_controller;

    private static final String TOUCHPOINT_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_touch_point_list_of_registered_journey";
    private static final String TAG_TOUCHPOINT = "touchPointDTOList";
    private static final String TAG_TOUCHPOINTDESCRIPTION = "touchPointDesc";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpoint_list);

        Bundle extras = getIntent().getExtras();
        //JourneyName = (String) extras.get("JourneyName");
        Username = (String) extras.get("Username");

        listView = (ListView) findViewById(R.id.touchpointlistview);

        touchList = new ArrayList<TouchpointList_Model>();

        getJSONData();

        touchpoint_controller = new TouchpointList_Controller(this,R.layout.list_row,touchList);
        listView.setAdapter(touchpoint_controller);

        new HttpRequestTask().execute();
    }

    private void getJSONData() {
        JSONObject request = new JSONObject();
        /*try {
            request = new JSONObject("{\"\"\"username\"\"\":\"\"\"Gunjan\"\"\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
       try {
            request.put('"'+'"'+"username"+'"'+'"', Username+"\"\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Request",request.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                TOUCHPOINT_URL, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObj;
                Log.d("JOURNEYresponse" ,response.toString());
                if (response != null) {
                    try {
                        jsonObj = new JSONObject(response.toString());

                        JSONArray list = jsonObj.getJSONArray(TAG_TOUCHPOINT);

                        // looping through All Contacts
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject c = list.getJSONObject(i);
                            TouchpointList_Model item = new TouchpointList_Model();
                            item.setName(c.getString(TAG_TOUCHPOINTDESCRIPTION));
                            item.setChannel(c.getString(TAG_TOUCHPOINTDESCRIPTION));
                            item.setStatus(c.getString(TAG_TOUCHPOINTDESCRIPTION));
                            touchList.add(item);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, TouchPointFieldResearcherListDTO> {
        @Override
        protected TouchPointFieldResearcherListDTO doInBackground(Void... params) {
            try {
                final String url = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_touch_point_list_of_registered_journey";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername("Gunjan");
                TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO =
                        restTemplate.getForObject(url, TouchPointFieldResearcherListDTO.class, sdtUserDTO);
                for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO :
                        touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList()) {
                    Log.d("Touch Point Name", touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());
                }
                return touchPointFieldResearcherListDTO;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO) {

        }

    }

}

/*
    ArrayList<String> touchpointList;
    ListView listView;
    ArrayAdapter<String> touchpointAdapter;
    String JourneyName,Username;

    private static final String TOUCHPOINT_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_touch_point_list_of_journey";
    private static final String TAG_TOUCHPOINT = "touchPointDTOList";
    private static final String TAG_TOUCHPOINTDESCRIPTION = "touchPointDesc";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpoint_list);

        Bundle extras = getIntent().getExtras();
        JourneyName = (String) extras.get("JourneyName");
        Username = (String) extras.get("Username");

        listView=(ListView)findViewById(R.id.touchpointlistview);
        touchpointList = new ArrayList<String>();

        JSONObject request = new JSONObject();
        try {
            request.put("journeyName", JourneyName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                TOUCHPOINT_URL, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObj;
                Log.d("JOURNEYresponse" ,response.toString());
                if (response != null) {
                    try {
                        jsonObj = new JSONObject(response.toString());

                        JSONArray list = jsonObj.getJSONArray(TAG_TOUCHPOINT);

                        // looping through All Contacts
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject c = list.getJSONObject(i);

                            touchpointList.add(c.getString(TAG_TOUCHPOINTDESCRIPTION));
                        }
                        Log.d("JourneyListBefore" ,touchpointList.toString());

                        touchpointAdapter = new ArrayAdapter<String>(TouchpointList.this,android.R.layout.simple_list_item_1 ,touchpointList);

                        listView.setAdapter(touchpointAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                touchpointAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String touchpoint = (String) listView.getItemAtPosition(position);
                Log.d("PRESSED",touchpoint);
                Toast.makeText(getApplicationContext(), "Touchpoint Selected : "+touchpoint, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(TouchpointList.this,TouchpointDetails.class);
                i.putExtra("Touchpoint",touchpoint);
                i.putExtra("Username",Username);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.touch_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                Toast.makeText(this, "Pressed Map",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TouchpointList.this,MapsActivity.class);
                startActivity(i);
                return true;
        }
        return false;
    }
}
*/
