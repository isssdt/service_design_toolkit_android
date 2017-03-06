package addTouchpoint;

/**
 * Created by Gunjan on 01-Mar-17.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.dto.MasterDataDTO;
import common.dto.RESTResponse;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journeyVisualization.Journey_Visualization;
import photo.SelectPhoto;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.TouchpointDetails;
import touchpoint.dto.ChannelDTO;
import touchpoint.dto.RatingDTO;
import touchpoint.dto.TouchPointDTO;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan on 01-Mar-17.
 */

public class AddNewTouchpoint extends AppCompatActivity implements View.OnClickListener {
    EditText touchpointName_edit,touchpoint_name_after_edit,channelDescription_edit,time_taken,reaction_edit,comment_edit,imagepathEdit,action_edit;
    TextView image;
    RatingBar ratingBar;
    Spinner time,channel,touchpoint;
    Button submit;
    ImageButton photo;
    List<String> time_data,channel_data;
    String Username;

    String touchpoint_name,touchpoint_after,touchpoint_before,action,channel_name,channel_description,actual_time,time_unit,rating,reaction,comment,imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_touchpoint);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Trial", MODE_PRIVATE);
        Username = pref.getString("Username","Username");

        touchpointName_edit = (EditText) findViewById(R.id.touchpoint_name_edit);
        touchpoint_name_after_edit = (EditText)findViewById(R.id.touchpoint_name_after);
        action_edit = (EditText) findViewById(R.id.action_edit);
        channelDescription_edit = (EditText) findViewById(R.id.channelDescription_edit);
        time_taken = (EditText) findViewById(R.id.action);
        reaction_edit = (EditText) findViewById(R.id.reaction_edit);
        comment_edit = (EditText) findViewById(R.id.comment_edit);
        image = (TextView) findViewById(R.id.imagelabel);
        imagepathEdit = (EditText) findViewById(R.id.imagePathEdit);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        submit = (Button) findViewById(R.id.submit);
        photo = (ImageButton) findViewById(R.id.photo);

        time = (Spinner) findViewById(R.id.time_unit);
        channel = (Spinner) findViewById(R.id.channel);
        touchpoint = (Spinner) findViewById(R.id.touchpoint);

        spinnerData();

        submit.setOnClickListener(this);
        photo.setOnClickListener(this);
    }

    private void spinnerData() {
        time_data = new ArrayList<String>();
        time_data.add("Minute");
        time_data.add("Hour");
        time_data.add("Day");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time_data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(dataAdapter);

        channel_data = new ArrayList<String>();
        channel_data.add("Website");
        channel_data.add("kiosk");
        channel_data.add("Face to Face");

        ArrayAdapter<String> channelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, channel_data);
        channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        channel.setAdapter(channelAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                //new AddTouchPoint().execute();
                break;
            case R.id.photo:
                getdetails();
                Intent i = new Intent(AddNewTouchpoint.this, SelectPhoto.class);
                startActivity(i);
                break;
        }
    }

    private void getdetails() {
        touchpoint_name = touchpointName_edit.getText().toString();
        touchpoint_after = "Empty" ;
        touchpoint_before = touchpoint_name_after_edit.getText().toString();
        action = action_edit.getText().toString();
        channel_name = channel.getSelectedItem().toString();
        channel_description = channelDescription_edit.getText().toString();
        actual_time = time_taken.getText().toString();
        time_unit = time.getSelectedItem().toString();
        rating = String.valueOf(ratingBar.getRating());
        reaction = reaction_edit.getText().toString();
        comment = comment_edit.getText().toString();
        imagepath = imagepathEdit.getText().toString();

    }

    private class AddTouchPoint extends AsyncTask<Void, Void, RESTResponse> {
        @Override
        protected RESTResponse doInBackground(Void... params) {
            try {
                final String url = APIUrl.API_ADD_TOUCHPOINT;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(Username);
                fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);

                MasterDataDTO masterDataDTO = new MasterDataDTO();
                masterDataDTO.setDataValue(time_unit);

                ChannelDTO channelDTO = new ChannelDTO();
                channelDTO.setChannelName(channel_name);

                JourneyDTO journeyDTO = new JourneyDTO();
                journeyDTO.setJourneyName(Journey);

                TouchPointDTO touchPointDTO = new TouchPointDTO();
                touchPointDTO.setId(Integer.parseInt(id_String));
                touchPointDTO.setAction(action);
                touchPointDTO.setChannelDescription(channel_description);
                touchPointDTO.setDuration(Integer.valueOf(actual_time));
                touchPointDTO.setMasterDataDTO(masterDataDTO);
                touchPointDTO.setChannelDTO(channelDTO);
                touchPointDTO.setJourneyDTO(journeyDTO);

                TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();
                touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
                touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
                //touchPointFieldResearcherDTO.setComments(comment);
                //touchPointFieldResearcherDTO.setReaction(reaction);
                //touchPointFieldResearcherDTO.setDuration(Integer.parseInt(actual_time));
                //touchPointFieldResearcherDTO.setPhotoLocation(imagepath);

                Gson gson = new Gson();
                String json = gson.toJson(touchPointFieldResearcherDTO);

                Log.d("DATA",json);

                RESTResponse response =
                        restTemplate.postForObject(url, touchPointFieldResearcherDTO, RESTResponse.class);
                String message = response.getMessage();
                Log.d("message",message);
                /*Intent i = new Intent(TouchpointDetails.this, Journey_Visualization.class);
                JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
                journeyFieldResearcherDTO.setJourneyDTO(new JourneyDTO());
                journeyFieldResearcherDTO.getJourneyDTO().setJourneyName(JourneyName);
                journeyFieldResearcherDTO.setFieldResearcherDTO(new FieldResearcherDTO());
                journeyFieldResearcherDTO.getFieldResearcherDTO().setSdtUserDTO(new SdtUserDTO());
                journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().setUsername(username);
                i.putExtra(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
                i.putExtra("Username", username);
                i.putExtra("JourneyName", JourneyName);
                startActivity(i);*/
                return response;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            reaction_string = reaction_edit.getText().toString();
            comment_string = comment_edit.getText().toString();
            rating = (int) ratingBar.getRating();
            rating_string = Integer.toString(rating);
        }

        @Override
        protected void onPostExecute(RESTResponse response) {
        }
    }
}
