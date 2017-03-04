package poc.servicedesigntoolkit.getpost;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

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
import photo.SelectPhoto;
import touchpoint.activity.TouchPointListActivity;
import touchpoint.dto.RatingDTO;
import touchpoint.dto.TouchPointDTO;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan Pathak on 06-Oct-16.
 */

public class TouchpointDetails extends AppCompatActivity implements View.OnClickListener,LocationListener {

    private static final String completed_confirmation = "Journey has been marked as Completed";
    private static final int take_photo_request_code =1;
    private static final int share_location_request_code = 2;
    EditText touchpointName_edit,imagepathEdit, channelDescription_edit, channel_edit, action_edit, comment_edit, reaction_edit,expected_edit,actual_edit;
    RatingBar ratingBar;
    TextView image;
    Button submit, reset;
    Spinner time ;
    ImageButton photo;
    String touchpoint, username, actual_time,actual_unit, JourneyName, reaction_string, comment_string;
    String name, action, channel_desc, channel,expected_unit;
    double lat,lng;
    Integer expected_time;
    Integer id;
    List<String> time_data;
    String id_String, rating_string;
    String rating_intent,reaction_intent,comment_intent, actual_string,actual_time_unit,imagepath;
    int rating;
    private LocationManager locationManager;
    String message = "",provider;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpoint_details_1);

        Bundle extras = getIntent().getExtras();
        touchpoint = (String) extras.get("Touchpoint");
        username = (String) extras.get("Username");
        JourneyName = (String) extras.get("JourneyName");
        action = (String) extras.get("Action");
        channel = (String) extras.get("Channel");
        expected_time = (Integer) extras.get("Expected_time");
        expected_unit = (String) extras.get("Expected_unit");
        channel_desc = (String) extras.get("Channel_Desc");
        id = (Integer) extras.get("Id");
        id_String = id.toString();
        rating_intent = (String) extras.get("rating");
        reaction_intent = (String) extras.get("reaction");
        comment_intent = (String) extras.get("comment");
        actual_time = (String) extras.get("Actual_time");
        actual_unit = (String) extras.get("Actual_unit");
        imagepath = (String) extras.get("image");

        touchpointName_edit = (EditText) findViewById(R.id.touchpoint_name);
        channel_edit = (EditText) findViewById(R.id.channel);
        channelDescription_edit = (EditText) findViewById(R.id.Name);
        action_edit = (EditText) findViewById(R.id.action);
        reaction_edit = (EditText) findViewById(R.id.reaction);
        comment_edit = (EditText) findViewById(R.id.comment);
        expected_edit = (EditText) findViewById(R.id.expected);
        actual_edit = (EditText) findViewById(R.id.actual_time);

        image = (TextView) findViewById(R.id.imagelabel);
        imagepathEdit = (EditText) findViewById(R.id.imagePathEdit);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        submit = (Button) findViewById(R.id.submit);
       // reset = (Button) findViewById(R.id.reset);
        photo = (ImageButton) findViewById(R.id.photo);

        time = (Spinner) findViewById(R.id.time_unit);

        submit.setOnClickListener(this);
//        reset.setOnClickListener(this);
        photo.setOnClickListener(this);

        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);

        setText();

    }

    public void setText() {

        List<String> time_data = new ArrayList<String>();
        time_data.add("Minute");
        time_data.add("Hour");
        time_data.add("Day");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time_data);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(dataAdapter);

        int position = time_data.indexOf(actual_unit);
        Log.d("Position", ""+position);
        touchpointName_edit.setText(touchpoint);
        channel_edit.setText(channel);
        action_edit.setText(action);
        expected_edit.setText(expected_time.toString() + " " + expected_unit );
        channelDescription_edit.setText(channel_desc);
        if(null != rating_intent){
            ratingBar.setRating(Float.parseFloat(rating_intent));
            ratingBar.setIsIndicator(true);
            reaction_edit.setText(reaction_intent);
            comment_edit.setText(comment_intent);
            actual_edit.setText(actual_time);
            time.setSelection(position);
        }
        if (null != imagepath){
            if ("0".equals(rating_intent)){
                ratingBar.setIsIndicator(false);
            }
            ratingBar.setEnabled(true);
            image.setVisibility(View.VISIBLE);
            imagepathEdit.setVisibility(View.VISIBLE);
            imagepathEdit.setEnabled(false);
            imagepathEdit.setText(imagepath);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == submit) {
            if (validate()) {
                getdetails();
                locationUpdate();
                if(time.getSelectedItem().equals("Minute") && (Integer.parseInt(String.valueOf(actual_edit.getText())) > 60)){
                    Toast.makeText(this, "Minutes cannot be greater then 60", Toast.LENGTH_SHORT).show();
                }else if(time.getSelectedItem().equals("Hour") && (Integer.parseInt(String.valueOf(actual_edit.getText())) > 24)){
                    Toast.makeText(this, "Hours cannot be greater then 24", Toast.LENGTH_SHORT).show();
                }else{
                    new HttpRequestTask().execute();
                }
                if(message.equals(completed_confirmation)){
                    Toast.makeText(this, "Your Journey is Completed.", Toast.LENGTH_SHORT).show();
                }
            }
        }/* else if (v == reset) {
            ratingBar.setRating(0);
            reaction_edit.setText("");
            comment_edit.setText("");

        }*/ else if (v == photo) {
            if (ContextCompat.checkSelfPermission(TouchpointDetails.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TouchpointDetails.this, new String[]{Manifest.permission.CAMERA}, take_photo_request_code);
            } else {
                getdetails();
                Intent i = new Intent(TouchpointDetails.this, SelectPhoto.class);
                i.putExtra("Touchpoint",touchpoint);
                i.putExtra("Username",username);
                i.putExtra("JourneyName", JourneyName);

                i.putExtra("Action", action);
                i.putExtra("Channel", channel);
                i.putExtra("Channel_Desc",channel_desc );
                i.putExtra("Id", id);
                i.putExtra("Expected_time",expected_time);
                i.putExtra("Expected_unit",expected_unit);

                if (null != String.valueOf(ratingBar.getRating())){
                    i.putExtra("rating",rating_string);
                    i.putExtra("comment",comment_string);
                    i.putExtra("reaction",reaction_string);
                    i.putExtra("Actual_time",actual_string);
                    i.putExtra("Actual_unit",actual_time_unit);
                }

                startActivity(i);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case take_photo_request_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(TouchpointDetails.this, SelectPhoto.class);
                    i.putExtra("Touchpoint",touchpoint);
                    i.putExtra("Username",username);
                    i.putExtra("JourneyName", JourneyName);

                    i.putExtra("Action", action);
                    i.putExtra("Channel", channel);
                    i.putExtra("Channel_Desc",channel_desc );
                    i.putExtra("Id", id);
                    i.putExtra("Expected_time",expected_time);
                    i.putExtra("Expected_unit",expected_unit);

                    if (null != String.valueOf(ratingBar.getRating())){
                        i.putExtra("rating",rating_string);
                        i.putExtra("comment",comment_string);
                        i.putExtra("reaction",reaction_string);
                        i.putExtra("Actual_time",actual_string);
                        i.putExtra("Actual_unit",actual_time_unit);
                    }

                    startActivity(i);
                } else {
                    Toast.makeText(TouchpointDetails.this, "Permission denied to read your CAMERA", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case share_location_request_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(TouchpointDetails.this, "Permission denied to get your LOCATION", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private boolean validate() {
        if (reaction_edit.getText().length() == 0 && ((int) ratingBar.getRating()) == 0.0) {
            Toast.makeText(TouchpointDetails.this, "Please enter the Rating and Reaction", Toast.LENGTH_SHORT).show();
            return false;
        } else if (((int) ratingBar.getRating()) == 0.0) {
            Toast.makeText(TouchpointDetails.this, "Please enter the Rating", Toast.LENGTH_SHORT).show();
            return false;
        } else if (reaction_edit.getText().length() == 0) {
            Toast.makeText(TouchpointDetails.this, "Please enter what did you do", Toast.LENGTH_SHORT).show();
            return false;
        } else if (actual_edit.getText().length() == 0) {
            Toast.makeText(TouchpointDetails.this, "Please enter the Time Taken", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void locationUpdate(){
        if (ContextCompat.checkSelfPermission(TouchpointDetails.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TouchpointDetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, share_location_request_code);
        } else {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            }else {
                Toast.makeText(getApplicationContext(),"Location not available",Toast.LENGTH_SHORT );
            }
        }
    }
    private void getdetails() {
        reaction_string = reaction_edit.getText().toString();
        comment_string = comment_edit.getText().toString();
        rating = (int) ratingBar.getRating();
        rating_string = Integer.toString(rating);
        actual_string = actual_edit.getText().toString();
        actual_time_unit = time.getSelectedItem().toString();
        Log.d("actual_time_unit",time.getSelectedItem().toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        new LocationUpdate().execute();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, RESTResponse> {
        @Override
        protected RESTResponse doInBackground(Void... params) {
            try {
                final String url = APIUrl.API_UPDATE_RESEARCH_WORK;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();


                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                Log.d("username",username);
                sdtUserDTO.setUsername(username);
                fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);

                TouchPointDTO touchPointDTO = new TouchPointDTO();
                touchPointDTO.setId(Integer.parseInt(id_String));

                TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();
                touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
                touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
                touchPointFieldResearcherDTO.setComments(comment_string);
                touchPointFieldResearcherDTO.setReaction(reaction_string);
                touchPointFieldResearcherDTO.setDuration(Integer.parseInt(actual_string));
                touchPointFieldResearcherDTO.setPhotoLocation(imagepath);

                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setValue(rating_string);
                touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);


                MasterDataDTO masterDataDTO = new MasterDataDTO();
                masterDataDTO.setDataValue(actual_time_unit);
                touchPointFieldResearcherDTO.setDurationUnitDTO(masterDataDTO);

                Gson gson = new Gson();
                String json = gson.toJson(touchPointFieldResearcherDTO);

                Log.d("DATA",json);

                RESTResponse response =
                        restTemplate.postForObject(url, touchPointFieldResearcherDTO, RESTResponse.class);
                message = response.getMessage();
                Log.d("message",message);
                Intent i = new Intent(TouchpointDetails.this, TouchPointListActivity.class);
                JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
                journeyFieldResearcherDTO.setJourneyDTO(new JourneyDTO());
                journeyFieldResearcherDTO.getJourneyDTO().setJourneyName(JourneyName);
                journeyFieldResearcherDTO.setFieldResearcherDTO(new FieldResearcherDTO());
                journeyFieldResearcherDTO.getFieldResearcherDTO().setSdtUserDTO(new SdtUserDTO());
                journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().setUsername(username);
                i.putExtra(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
                    i.putExtra("Username", username);
                    i.putExtra("JourneyName", JourneyName);
                    startActivity(i);
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

    private class LocationUpdate extends AsyncTask<Void, Void, RESTResponse> {

        @Override
        protected RESTResponse doInBackground(Void... params) {
        try{
            final String url = APIUrl.API_UDPDATE_FIELD_RESEARCHER_CURRENT_LOCATION;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            SdtUserDTO sdtUserDTO = new SdtUserDTO();
            sdtUserDTO.setUsername(username);

            FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
            fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
            fieldResearcherDTO.setCurrentLatitude(String.valueOf(lat));
            fieldResearcherDTO.setCurrentLongitude(String.valueOf(lng));

            RESTResponse response =
                    restTemplate.postForObject(url,fieldResearcherDTO , RESTResponse.class);
            String locationmessage = response.getMessage();
            Log.d("Location : ", locationmessage);
            Log.d("Location : ", fieldResearcherDTO.toString());

        }catch (Exception e){
        Log.d("Location ","In Exception ");
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
