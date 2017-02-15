package journeyVisualization;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.dto.RESTResponse;
import journey.dto.JourneyFieldResearcherDTO;
import poc.servicedesigntoolkit.getpost.MainActivity;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointAdapter;
import poc.servicedesigntoolkit.getpost.Touchpoint.Touchpoint_model;
import poc.servicedesigntoolkit.getpost.TouchpointDetails;
import timeline.RoundTimelineView;
import timeline.TimelineView;
import touchpoint.activity.TouchPointListActivity;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;

import static poc.servicedesigntoolkit.getpost.R.id.submitJourney;

/**
 * Created by Gunjan Pathak on 11/01/2017.
 */

public class Journey_Visualization extends AppCompatActivity {

    String Username, JourneyName,JourneyDesc, Message,Expected_time;
    String TOUCHPOINTLIST_URL = APIUrl.API_GET_TOUCH_POINT_LIST_OF_REGISTERED_JOURNEY;
    private static final String COMPLETE_URL = APIUrl.API_MARK_JOURNEY_COMPLETED;
    ArrayList<Touchpoint_model> touchpointData;
    ListView list;
    TextView journeyName,journeyDesc;
    Button submitJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visulaization_journey);

        Bundle extras = getIntent().getExtras();
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO);
        /*if (null != journeyFieldResearcherDTO.getJourneyDTO().getJourneyName()) {
            JourneyName = journeyFieldResearcherDTO.getJourneyDTO().getJourneyName();
        }*/
        Username = ((JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO)).getFieldResearcherDTO().getSdtUserDTO().getUsername();
        Message = (String) extras.get("Message");

        touchpointData = new ArrayList<Touchpoint_model>();

        submitJourney = (Button) findViewById(R.id.submitJourney1);
        list = (ListView) findViewById(R.id.list);
        journeyName = (TextView) findViewById(R.id.displayJourneyName);
        journeyDesc = (TextView) findViewById(R.id.displayJourneyDesc);
        new HttpRequestTask().execute();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView < ? > adapter, View view, int position, long arg){
                    Touchpoint_model model = touchpointData.get(position);

                    Log.d("Expected",model.getDuration().toString());

                    Intent i = new Intent(Journey_Visualization.this, TouchpointDetails.class);
                    i.putExtra("Action", model.getAction());
                    i.putExtra("Channel", model.getChannel());
                    i.putExtra("Channel_Desc", model.getChannel_desc());
                    i.putExtra("Name", model.getName());
                    i.putExtra("Id", model.getId());
                    i.putExtra("Username", Username);
                    i.putExtra("JourneyName", JourneyName);
                    i.putExtra("Expected_time",model.getDuration());
                    i.putExtra("Expected_unit",model.getExpectedunit());

                    if (null != model.getRating()){
                        i.putExtra("rating",model.getRating());
                        i.putExtra("comment",model.getComment());
                        i.putExtra("reaction",model.getReaction());
                        i.putExtra("Actual_time",model.getActualduration().toString());
                        i.putExtra("Actual_unit",model.getActual_unit());
                    }
                    startActivity(i);
                }
            }
        );

        submitJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        Journey_Visualization.this);
                adb.setTitle("Submit Journey");
                adb.setMessage(" Thank you for your response.Please proceed for new journey registeration");
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

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(
                Journey_Visualization.this);
        exit.setMessage(" Are you sure you want to exit Journey ?");
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Journey_Visualization.this, MainActivity.class);
                startActivity(intent);
            }
        });
        exit.setNegativeButton("No", null);
        exit.show();
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

                    if (null != touchPointFieldResearcherDTO.getRatingDTO().getValue()){
                        model.setRating(touchPointFieldResearcherDTO.getRatingDTO().getValue());
                        model.setReaction(touchPointFieldResearcherDTO.getReaction());
                        model.setComment(touchPointFieldResearcherDTO.getComments());
                        model.setActualduration(touchPointFieldResearcherDTO.getDuration());
                        model.setActual_unit(touchPointFieldResearcherDTO.getDurationUnitDTO().getDataValue());
                    }

                    model.setDuration(touchPointFieldResearcherDTO.getTouchpointDTO().getDuration());
                    model.setExpectedunit(touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getDataValue());
                    model.setId(touchPointFieldResearcherDTO.getTouchpointDTO().getId());
                    model.setChannel_desc(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDescription());
                    model.setAction(touchPointFieldResearcherDTO.getTouchpointDTO().getAction());
                    JourneyName = touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO().getJourneyName();
                    JourneyDesc = touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO().getDescription();
                    touchpointData.add(model);
                }
                return touchPointFieldResearcherListDTO;
            } catch (Exception e) {
                Log.e("Touchpoint", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO) {
            list.setAdapter(new visualization_adapter(getApplicationContext(),touchpointData));
            journeyName.setText(JourneyName);
            journeyDesc.setText(JourneyDesc);
            Integer i = 0;
            for (Touchpoint_model touchpoint_model: touchpointData) {
                if (touchpoint_model.getStatus().equals("DONE")){
                    i++;
                }
            }
            if(i == touchpointData.size())
                submitJourney.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(Journey_Visualization.this, MainActivity.class);
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
