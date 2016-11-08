package poc.servicedesigntoolkit.getpost.Touchpoint;

/**
 * Created by Gunjan Pathak on 28-Oct-16.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;

public class TouchpointMain extends AppCompatActivity  {

    List<Touchpoint_model> touchpointData;

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
    private static final String completed_confirmation = "Journey has been marked as Completed";
    private static final String TAG_USERNAME = "username";

    String JourneyName, Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpoint_recycle);

        Bundle extras = getIntent().getExtras();
        JourneyName = (String) extras.get("JourneyName");
        Username = (String) extras.get("Username");

        touchpointData = new ArrayList<Touchpoint_model>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);

        new HttpRequestTask().execute();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Touchpoint_model model = touchpointData.get(position);

                if (!model.getStatus().equals("DONE")) {
                    Intent i = new Intent(TouchpointMain.this, TouchpointDetails.class);
                    i.putExtra("Action", model.getChannel());
                    i.putExtra("Channel", model.getChannel());
                    i.putExtra("Channel_Desc", model.getChannel_desc());
                    i.putExtra("Name", model.getName());
                    i.putExtra("Id", model.getId());
                    i.putExtra("Username", Username);
                    i.putExtra("JourneyName", JourneyName);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "You have submitted response for this touchpoint", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, TouchPointFieldResearcherListDTO> {
        @Override
        protected TouchPointFieldResearcherListDTO doInBackground(Void... params) {
            try {
                final String url = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_touch_point_list_of_registered_journey";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(Username);
                TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO =
                        restTemplate.postForObject(url, sdtUserDTO, TouchPointFieldResearcherListDTO.class);


                for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO :
                        touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList()) {

                    Touchpoint_model model = new Touchpoint_model(
                            touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc(),
                            touchPointFieldResearcherDTO.getStatus(),
                            touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO().getChannelName());
                    touchpointData.add(model);
                    model.setId(touchPointFieldResearcherDTO.getTouchpointDTO().getId());
                    model.setChannel_desc(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDescription());
                    model.setAction(touchPointFieldResearcherDTO.getTouchpointDTO().getAction());

                }
                return touchPointFieldResearcherListDTO;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO) {
            LinearLayoutManager llm = new LinearLayoutManager(TouchpointMain.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewadapter = new TouchpointAdapter(touchpointData, TouchpointMain.this);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(recyclerViewadapter);
            recyclerViewadapter.notifyDataSetChanged();
        }

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
    }
}