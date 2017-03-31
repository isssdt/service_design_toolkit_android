package addTouchpoint;

/**
 * Created by Gunjan on 01-Mar-17.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import common.constants.APIUrl;
import common.dto.MasterDataDTO;
import common.dto.RESTResponse;
import common.utils.Utils;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import photo.SelectPhoto;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.activity.TouchPointListActivity;
import touchpoint.dto.ChannelDTO;
import touchpoint.dto.RatingDTO;
import touchpoint.dto.TouchPointDTO;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan on 01-Mar-17.
 */

public class AddNewTouchpoint extends AppCompatActivity implements View.OnClickListener{
    EditText touchpointName_edit,channelDescription_edit,time_taken,reaction_edit,comment_edit,imagepathEdit,action_edit;
    TextView image;
    RatingBar ratingBar;
    Spinner time,channel,touchpoint;
    Button submit;
    ImageButton photo;
    List<String> time_data,channel_data,touchpoint_data;
    String Username,JourneyName;
    int id;
    TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO;

    String touchpoint_name,action,channel_name,channel_description,actual_time,time_unit,rating,reaction,comment,imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_touchpoint);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Trial", MODE_PRIVATE);
       // Username = pref.getString("Username","Username");
        //Log.d("Usershared",Username);
        Gson gson = new Gson();

        String journey = pref.getString("JourneyFieldResearcherDTO", "");
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = gson.fromJson(journey, JourneyFieldResearcherDTO.class);
        Username =journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername();
        //Log.d("Usershared NAME",journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());

        String list = pref.getString("TouchPointFieldResearcherListDTO", "");
        touchPointFieldResearcherListDTO = gson.fromJson(list, TouchPointFieldResearcherListDTO.class);
        //Log.d("TouchPointFieldResearcherListDTO",list);

        touchpoint_data = new ArrayList<String>();
        for(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO:touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList()){
            //Log.d("TouchPointFieldResearcherListDTO",touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());
            touchpoint_data.add(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());
            JourneyName = touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO().getJourneyName();
        }


        touchpointName_edit = (EditText) findViewById(R.id.touchpoint_name_edit);
        action_edit = (EditText) findViewById(R.id.action_edit);
        channelDescription_edit = (EditText) findViewById(R.id.channelDescription_edit);
        time_taken = (EditText) findViewById(R.id.time_taken);
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

        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) getIntent().getExtras().get(TouchPointFieldResearcherDTO.class.toString());
        if (null != touchPointFieldResearcherDTO) {
            imagepathEdit.setText(touchPointFieldResearcherDTO.getPhotoLocation());
        }

    }

    private void spinnerData() {

        ArrayAdapter<String> touchpointAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, touchpoint_data);
        touchpointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        touchpoint.setAdapter(touchpointAdapter);



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
                getdetails();
                if(validate()){
                    new AddTouchPoint().execute();
                    Intent newIntent = new Intent(AddNewTouchpoint.this, TouchPointListActivity.class);
                    JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
                    journeyFieldResearcherDTO.setFieldResearcherDTO(new FieldResearcherDTO());
                    journeyFieldResearcherDTO.getFieldResearcherDTO().setSdtUserDTO(new SdtUserDTO());
                    journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().setUsername(Username);
                    journeyFieldResearcherDTO.setJourneyDTO(new JourneyDTO());
                    journeyFieldResearcherDTO.getJourneyDTO().setJourneyName(JourneyName);
                    newIntent.putExtra(JourneyFieldResearcherDTO.class.toString(), journeyFieldResearcherDTO);
                    startActivity(newIntent);
                }
                break;
            case R.id.photo:
                getdetails();
                Intent i = new Intent(AddNewTouchpoint.this, SelectPhoto.class);
                TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();
                i.putExtra(TouchPointFieldResearcherDTO.class.toString(), touchPointFieldResearcherDTO);
                i.putExtra(Activity.class.toString(), getClass().toString());
                startActivity(i);
                break;
        }
    }

    private void getdetails() {
        touchpoint_name = touchpointName_edit.getText().toString();
        action = action_edit.getText().toString();
        channel_name = channel.getSelectedItem().toString();
        channel_description = channelDescription_edit.getText().toString();
        actual_time = time_taken.getText().toString();
        time_unit = time.getSelectedItem().toString();
        rating = String.valueOf((int) ratingBar.getRating());
        reaction = reaction_edit.getText().toString();
        comment = comment_edit.getText().toString();
        imagepath = imagepathEdit.getText().toString();

        for(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO:touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList()){
            if(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc().equals(touchpoint.getSelectedItem().toString())){
                id =touchPointFieldResearcherDTO.getTouchpointDTO().getId();
            }
        }
    }

    private boolean validate(){
        if(TextUtils.isEmpty(touchpointName_edit.getText().toString())) {
            touchpointName_edit.setError("Please Enter the Touchpoint Name");
            touchpointName_edit.setFocusable(true);
            return false;
        } else if (TextUtils.isEmpty(action_edit.getText().toString())) {
            action_edit.setError("Please Enter the Action Performed");
            action_edit.setFocusable(true);
            return false;
        }else if (TextUtils.isEmpty(channelDescription_edit.getText().toString())){
            channelDescription_edit.setError("Please Enter the details of Channel");
            channelDescription_edit.setFocusable(true);
            return false;
        }else if (TextUtils.isEmpty(time_taken.getText().toString())){
            time_taken.setError("Please Enter the Time taken to complete");
            time_taken.setFocusable(true);
            return false;
        }else if (0 == ratingBar.getRating()){
            Toast.makeText(getApplicationContext(), "Please Enter the Rating ", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(reaction_edit.getText().toString())){
            reaction_edit.setError("Please Enter What did you do");
            reaction_edit.setFocusable(true);
            return false;
        }
        return true;
    }

    private class AddTouchPoint extends AsyncTask<Void, Void, RESTResponse> {
        @Override
        protected RESTResponse doInBackground(Void... params) {
            try {
                final String url = APIUrl.API_ADD_TOUCHPOINT;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();

                FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
                Log.d("Username",Username);
                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(Username);
                Log.d("Username",sdtUserDTO.getUsername());


                fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);

                touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);

                TouchPointDTO touchPointDTO = new TouchPointDTO();
                touchPointDTO.setChannelDescription(channel_description);
                touchPointDTO.setAction(action);
                touchPointDTO.setTouchPointDesc(touchpoint_name);
                touchPointDTO.setDuration(Integer.valueOf(actual_time));

                MasterDataDTO masterDataDTO = new MasterDataDTO();
                masterDataDTO.setDataValue(time_unit);
                touchPointDTO.setMasterDataDTO(masterDataDTO);

                ChannelDTO channelDTO = new ChannelDTO();
                channelDTO.setChannelName(channel_name);
                touchPointDTO.setChannelDTO(channelDTO);

                JourneyDTO journeyDTO = new JourneyDTO();
                journeyDTO.setJourneyName(JourneyName);
                touchPointDTO.setJourneyDTO(journeyDTO);

                touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);

                touchPointFieldResearcherDTO.setComments(comment);
                touchPointFieldResearcherDTO.setReaction(reaction);
                touchPointFieldResearcherDTO.setDuration(Integer.parseInt(actual_time));
                touchPointFieldResearcherDTO.setPhotoLocation(imagepath);
                touchPointFieldResearcherDTO.setTouchPointBeforeID(id);
                Log.d("ID", String.valueOf(id));

                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setValue(rating);
                touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);

                touchPointFieldResearcherDTO.setDurationUnitDTO(masterDataDTO);


                Gson gson = new Gson();
                String json = gson.toJson(touchPointFieldResearcherDTO);

                Log.d("DATA",json);

                RESTResponse response =
                        restTemplate.postForObject(url, touchPointFieldResearcherDTO, RESTResponse.class);
                String message = response.getMessage();
                Log.d("message",message);

                if (null != imagepath && !imagepath.isEmpty()) {
                    Utils.uploadImageThread(imagepath,
                            JourneyName + "_" + touchpoint_name + "_" +
                                    Username + ".jpg");
                }
                return response;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(RESTResponse response) {
        }
    }
}
