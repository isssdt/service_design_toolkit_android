package poc.servicedesigntoolkit.getpost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gunjan Pathak on 04-Oct-16.
 */

public class TouchpointList extends AppCompatActivity {
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
                return true;
        }
        return false;
    }
}
