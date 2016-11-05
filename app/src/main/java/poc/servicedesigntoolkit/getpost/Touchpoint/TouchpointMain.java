package poc.servicedesigntoolkit.getpost.Touchpoint;

/**
 * Created by Gunjan Pathak on 28-Oct-16.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import poc.servicedesigntoolkit.getpost.AppController;
import poc.servicedesigntoolkit.getpost.JourneyList;
import poc.servicedesigntoolkit.getpost.MainActivity;
import poc.servicedesigntoolkit.getpost.MapsActivity;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.TouchpointDetails;
import poc.servicedesigntoolkit.getpost.TouchpointList;

public class TouchpointMain extends AppCompatActivity  {

    List<TouchpointList_Model> touchpointData;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;

    String GET_JSON_DATA_HTTP_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_touch_point_list_of_registered_journey";

    String TAG_TOUCHPOINT = "touchPointDTOList";
    String TAG_DESC = "touchPointDesc";
    String TAG_CHANNEL = "channelName";
    String TAG_STATUS = "status";
    String TAG_ACTION = "status";
    String TAG_CHANNEL_DESC = "status";
    private static final String TAG_USERNAME = "username";


    JsonArrayRequest jsonArrayRequest;
    String JourneyName, Username;
    RequestQueue requestQueue;

    public static final String[] Name = new String[] { "Point_1",
            "Point_2", "Point_3", "Point_4" };

    public static final String[] Channel = new String[] {
            "Channel_1", "Channel_2", "Channel_3", "Channel_4" };

    public static final String[] Status = new String[] {
            "Status_1", "Status_2", "Status_3", "Status_4" };

    public static final String[] Channel_desc = new String[] {
            "Channel_DESC_1", "Channel_DESC_2", "Channel_DESC_3", "Channel_DESC_4" };

    public static final String[] Action = new String[] {
            "Action_1", "Action_2", "Action_3", "Action_4" };

    ListView listView;
    List<TouchpointList_Model> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpoint_recycle);

        Bundle extras = getIntent().getExtras();
        JourneyName = (String) extras.get("JourneyName");
        Username = (String) extras.get("Username");

        touchpointData = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        JSON_DATA_WEB_CALL();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewadapter = new TouchpointAdapter(touchpointData, TouchpointMain.this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(recyclerViewadapter);
        //recyclerView.setOnClickListener(this);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                //int itemPosition = recyclerView.getChildLayoutPosition(v);
                TouchpointList_Model model = touchpointData.get(position);
                Intent i = new Intent(TouchpointMain.this, TouchpointDetails.class);
                i.putExtra("Action",model.getAction());
                i.putExtra("Channel",model.getChannel());
                i.putExtra("Channel_Desc",model.getChannel_desc());
                i.putExtra("Name",model.getName());
                i.putExtra("Username",Username);
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void JSON_DATA_WEB_CALL(){
       // rowItems = new ArrayList<TouchpointList_Model>();
        for (int i = 0; i < Name.length; i++) {
            TouchpointList_Model item = new TouchpointList_Model(Name[i],Channel[i],Channel_desc[i],Action[i],Status[i]);
            touchpointData.add(item);
        }
    }

   /* public void JSON_DATA_WEB_CALL() {
        final JSONObject request = new JSONObject();
        try {
            request.put(TAG_USERNAME, Username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Request", request.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                GET_JSON_DATA_HTTP_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObj;
                Log.d("Response", response.toString());
                if (response != null) {
                    try {
                        jsonObj = new JSONObject(response.toString());

                        JSONArray list = jsonObj.getJSONArray(TAG_TOUCHPOINT);

                        // looping through All Contacts
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject c = list.getJSONObject(i);
                            TouchpointList_Model item = new TouchpointList_Model();
                            item.setName(c.getString(TAG_DESC));
                            item.setChannel(c.getString(TAG_CHANNEL));
                            item.setStatus(c.getString(TAG_STATUS));
                            item.setChannel_desc(c.getString(TAG_CHANNEL_DESC));
                            item.setAction(c.getString(TAG_ACTION));
                            touchpointData.add(item);
                        }

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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

            *//*@Override
            public Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put(TAG_USERNAME, Username);
                return param;
            }*//*

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }

                return volleyError;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

        recyclerViewadapter = new TouchpointAdapter(touchpointData, TouchpointMain.this);
        recyclerView.setAdapter(recyclerViewadapter);

    }*/

  /*  @Override
    public void onClick(View v) {
        int itemPosition = recyclerView.getChildLayoutPosition(v);
        TouchpointList_Model model = touchpointData.get(itemPosition);
        Intent i = new Intent(TouchpointMain.this, TouchpointDetails.class);
        i.putExtra("Action",model.getAction());
        i.putExtra("Channel",model.getChannel());
        i.putExtra("Channel_Desc",model.getChannel_desc());
        i.putExtra("Name",model.getName());
        i.putExtra("Username",Username);
        Log.d("Action",model.getAction());
        Log.d("Channel",model.getChannel());
        Log.d("channel_desc",model.getChannel_desc());
        Log.d("Username",Username);

        startActivity(i);

        String item = String.valueOf(touchpointData.get(itemPosition));
        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
    }*/

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
                Intent i = new Intent(TouchpointMain.this,MapsActivity.class);
                startActivity(i);
                return true;
        }
        return false;
    }
}
    /*public void JSON_DATA_WEB_CALL(){


        final JSONObject request = new JSONObject();
        try {
            request.put(TAG_USERNAME, Username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put(TAG_USERNAME, Username);

        Log.d("Request", request.toString());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET,
                GET_JSON_DATA_HTTP_URL,request, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                Log.d("Response", volleyError.toString());
                return volleyError;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put(TAG_USERNAME, Username);
                return params;
            }
        };
*//*
        final JSONObject request = new JSONObject();
        try {
            request.put(TAG_USERNAME, Username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("request", request.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                GET_JSON_DATA_HTTP_URL,request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObj;
                        Log.d("Response", response.toString());
                        *//*if (response != null) {
                            try {
                                jsonObj = new JSONObject(response.toString());

                                JSONArray list = jsonObj.getJSONArray(TAG_TOUCHPOINT);

                                // looping through All Contacts
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject c = list.getJSONObject(i);
                                    TouchpointList_Model item = new TouchpointList_Model();
                                    item.setName(c.getString(TAG_DESC));
                                    item.setChannel(c.getString(TAG_CHANNEL));
                                    item.setStatus(c.getString(TAG_STATUS));
                                    item.setChannel_desc(c.getString(TAG_CHANNEL_DESC));
                                    item.setAction(c.getString(TAG_ACTION));
                                    touchpointData.add(item);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }*//*
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Error: " + error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
            *//*@Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put(TAG_USERNAME, Username);
                return params;
            }*//*
        };
        Log.d("REQUEST",jsonObjReq.toString());
        //requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(jsonObjReq);
        AppController.getInstance().addToRequestQueue(jsonObjReq);

        recyclerViewadapter = new TouchpointAdapter(touchpointData, TouchpointMain.this);
        recyclerView.setAdapter(recyclerViewadapter);

    }

    @Override
    public void onClick(View v) {
        int itemPosition = recyclerView.getChildLayoutPosition(v);
        TouchpointList_Model model = touchpointData.get(itemPosition);
         Intent i = new Intent(TouchpointMain.this, TouchpointDetails.class);
        i.putExtra("Action",model.getAction());
        i.putExtra("Channel",model.getChannel());
        i.putExtra("Channel_Desc",model.getChannel_desc());
        i.putExtra("Name",model.getName());
        i.putExtra("Username",Username);

        startActivity(i);

        String item = String.valueOf(touchpointData.get(itemPosition));
        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
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
                Intent i = new Intent(TouchpointMain.this,MapsActivity.class);
                startActivity(i);
                return true;
        }
        return false;
    }*/