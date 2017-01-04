package touchpoint.activity;

/**
 * Created by Gunjan Pathak on 28-Oct-16.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import common.dto.RESTResponse;
import poc.servicedesigntoolkit.getpost.MainActivity;
import poc.servicedesigntoolkit.getpost.MapsActivity;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.Touchpoint.RecyclerTouchListener;
import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointAdapter;
import poc.servicedesigntoolkit.getpost.Touchpoint.Touchpoint_model;
import poc.servicedesigntoolkit.getpost.TouchpointDetails;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;

public class TouchPointListActivity extends AppCompatActivity {


    List<Touchpoint_model> touchpointData;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    private static final String touchpoint_complete = "Please informed that you have completed work for all Touch Points";
    String TOUCHPOINTLIST_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/get_touch_point_list_of_registered_journey";
    private static final String COMPLETE_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/journey_mark_complete";

    Button submitJourney;

    String JourneyName, Username, Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpoint_recycle);

        Bundle extras = getIntent().getExtras();
        JourneyName = (String) extras.get("JourneyName");
        Username = (String) extras.get("Username");
        Message = (String) extras.get("Message");

        submitJourney = (Button) findViewById(R.id.submitJourney);

        touchpointData = new ArrayList<Touchpoint_model>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);

        new HttpRequestTask().execute();

        submitJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        TouchPointListActivity.this);
                adb.setTitle("Submit Journey");
                adb.setMessage(" Journey will be submitted and no changes will be allowed");
                adb.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SdtUserDTO user = new SdtUserDTO();
                        user.setUsername(Username);
                        new CompleteJourney().execute();
                    }
                });
                adb.setNegativeButton("Cancel", null);
                adb.show();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Touchpoint_model model = touchpointData.get(position);
                Log.d("pos",""+position);
                if (position == 0){
                    Intent i = new Intent(TouchPointListActivity.this, TouchpointDetails.class);
                    i.putExtra("Action", model.getChannel());
                    i.putExtra("Channel", model.getChannel());
                    i.putExtra("Channel_Desc", model.getChannel_desc());
                    i.putExtra("Name", model.getName());
                    i.putExtra("Id", model.getId());
                    i.putExtra("Username", Username);
                    i.putExtra("JourneyName", JourneyName);
                    i.putExtra("Rating", model.getRating());
                    i.putExtra("Reaction", model.getReaction());
                    i.putExtra("Comment", model.getComment());
                    startActivity(i);
                }else if (position >= 1) {
                    if (touchpointData.get(position - 1).getStatus().equals("DONE")) {
                        Intent i = new Intent(TouchPointListActivity.this, TouchpointDetails.class);

                        i.putExtra("Action", model.getChannel());
                        i.putExtra("Channel", model.getChannel());
                        i.putExtra("Channel_Desc", model.getChannel_desc());
                        i.putExtra("Name", model.getName());
                        i.putExtra("Id", model.getId());
                        i.putExtra("Username", Username);
                        i.putExtra("JourneyName", JourneyName);
                        i.putExtra("Rating", model.getRating());
                        i.putExtra("Reaction", model.getReaction());
                        i.putExtra("Comment", model.getComment());
                        startActivity(i);
                    }else
                        Toast.makeText(TouchPointListActivity.this, "Please complete previous Touchpoint", Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(TouchPointListActivity.this, "Please complete previous Touchpoint", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


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
                Intent i = new Intent(TouchPointListActivity.this, MapsActivity.class);
                startActivity(i);
                return true;
        }
        return false;
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, TouchPointFieldResearcherListDTO> {
        @Override
        protected TouchPointFieldResearcherListDTO doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(Username);
                TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO =
                        restTemplate.postForObject(TOUCHPOINTLIST_URL, sdtUserDTO, TouchPointFieldResearcherListDTO.class);


                for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO :
                        touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList()) {

                    Touchpoint_model model = new Touchpoint_model(
                            touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc(),
                            touchPointFieldResearcherDTO.getStatus(),
                            touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO().getChannelName());
                            //touchPointFieldResearcherDTO.getRatingDTO().getValue(),
                            //touchPointFieldResearcherDTO.getReaction(),
                            //touchPointFieldResearcherDTO.getComments());
                    touchpointData.add(model);

                    model.setId(touchPointFieldResearcherDTO.getTouchpointDTO().getId());
                    model.setChannel_desc(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDescription());
                    model.setAction(touchPointFieldResearcherDTO.getTouchpointDTO().getAction());

                }
                return touchPointFieldResearcherListDTO;
            } catch (Exception e) {
                Log.e("Touchpoint", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO) {
            LinearLayoutManager llm = new LinearLayoutManager(TouchPointListActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewadapter = new TouchpointAdapter(touchpointData, TouchPointListActivity.this);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(recyclerViewadapter);
            recyclerViewadapter.notifyDataSetChanged();

            if((touchpointData.get(touchpointData.size()-1).getStatus()).equals("DONE")){
                submitJourney.setVisibility(View.VISIBLE);
            }
        }

    }

    private class CompleteJourney extends AsyncTask<Void, Void, RESTResponse> {
        @Override
        protected RESTResponse doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(Username);

                RESTResponse response =
                        restTemplate.postForObject(COMPLETE_URL, sdtUserDTO, RESTResponse.class);

                Message = response.getMessage();
                Log.d("Message",Message);
                Intent i = new Intent(TouchPointListActivity.this, MainActivity.class);
                i.putExtra("Message", Message);
                startActivity(i);

                return response;
            } catch (Exception e) {
                Log.e("Touchpoint", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(RESTResponse touchPointFieldResearcherListDTO) {
        }

    }
}