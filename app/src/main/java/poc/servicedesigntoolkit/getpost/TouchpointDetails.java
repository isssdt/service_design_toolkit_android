package poc.servicedesigntoolkit.getpost;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.dto.RESTResponse;
import journey.dto.JourneyFieldResearcherDTO;
import journeyVisualization.Journey_Visualization;
import poc.servicedesigntoolkit.getpost.journey.view.JourneyDTO;
import touchpoint.activity.TouchPointListActivity;
import touchpoint.dto.RatingDTO;
import touchpoint.dto.TouchPointDTO;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan Pathak on 06-Oct-16.
 */

public class TouchpointDetails extends AppCompatActivity implements View.OnClickListener {

    private static final String touchpoint_complete = "Please informed that you have completed work for all Touch Points";
    private static final String COMPLETE_URL = APIUrl.API_MARK_JOURNEY_COMPLETED;
    private static final String completed = "Please informed that you have completed work for all Touch Points";
    private static final String completed_confirmation = "Journey has been marked as Completed";
    private static final int take_photo_request_code =1;
    EditText touchpointName_edit, channelDescription_edit, channel_edit, action_edit, comment_edit, reaction_edit;
    RatingBar ratingBar;
    TextView image;
    Button submit, reset;
    ImageButton photo;
    String touchpoint, username, Reaction, Comment, JourneyName, reaction_string, comment_string;
    String name, action, channel_desc, channel;
    String Rating;
    Integer id;
    String id_String, rating_string;
    String rating_intent,reaction_intent,comment_intent;
    int rating;
    String message = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpoint_details);

        Bundle extras = getIntent().getExtras();

        touchpoint = (String) extras.get("Touchpoint");
        username = (String) extras.get("Username");
        JourneyName = (String) extras.get("JourneyName");
        action = (String) extras.get("Action");
        channel = (String) extras.get("Channel");
        channel_desc = (String) extras.get("Channel_Desc");
        name = (String) extras.get("Name");
        id = (Integer) extras.get("Id");
        id_String = id.toString();
        rating_intent = (String) extras.get("rating");
        reaction_intent = (String) extras.get("reaction");
        comment_intent = (String) extras.get("comment");

        touchpointName_edit = (EditText) findViewById(R.id.touchpoint_name);
        channel_edit = (EditText) findViewById(R.id.channel);
        channelDescription_edit = (EditText) findViewById(R.id.Name);
        action_edit = (EditText) findViewById(R.id.action);
        reaction_edit = (EditText) findViewById(R.id.reaction);
        comment_edit = (EditText) findViewById(R.id.comment);

        image = (TextView) findViewById(R.id.image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        submit = (Button) findViewById(R.id.submit);
        reset = (Button) findViewById(R.id.reset);
        photo = (ImageButton) findViewById(R.id.photo1);

        submit.setOnClickListener(this);
        reset.setOnClickListener(this);
        photo.setOnClickListener(this);

        setText();

    }

    public void setText() {

        touchpointName_edit.setText(name);
        channel_edit.setText(channel);
        action_edit.setText(action);
        channelDescription_edit.setText(channel_desc);
        if(null != rating_intent){
            ratingBar.setRating(Float.parseFloat(rating_intent));
            ratingBar.setIsIndicator(true);
            reaction_edit.setText(reaction_intent);
            comment_edit.setText(comment_intent);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == submit) {
            if (validate()) {
                getdetails();
                new HttpRequestTask().execute();
                if(message.equals(completed_confirmation)){
                    Toast.makeText(this, "Your Journey is Completed.", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v == reset) {
            ratingBar.setRating(0);
            reaction_edit.setText("");
            comment_edit.setText("");

        } else if (v == photo) {
            if (ContextCompat.checkSelfPermission(TouchpointDetails.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TouchpointDetails.this, new String[]{Manifest.permission.CAMERA}, take_photo_request_code);
            } else {
                Intent i = new Intent(TouchpointDetails.this, SelectPhoto.class);
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
                    startActivity(i);
                } else {
                    Toast.makeText(TouchpointDetails.this, "Permission denied to read your CAMERA", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(TouchpointDetails.this, "Please enter the Reaction", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getdetails() {
        reaction_string = reaction_edit.getText().toString();
        comment_string = comment_edit.getText().toString();
        rating = (int) ratingBar.getRating();
        rating_string = Integer.toString(rating);
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, RESTResponse> {
        @Override
        protected RESTResponse doInBackground(Void... params) {
            try {
                final String url = APIUrl.API_UPDATE_RESEARCH_WORK;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(username);

                FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
                fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);

                TouchPointDTO touchPointDTO = new TouchPointDTO();
                touchPointDTO.setId(Integer.parseInt(id_String));

                TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();
                touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
                touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
                touchPointFieldResearcherDTO.setComments(comment_string);
                touchPointFieldResearcherDTO.setReaction(reaction_string);

                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setValue(rating_string);
                touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);

                RESTResponse response =
                        restTemplate.postForObject(url, touchPointFieldResearcherDTO, RESTResponse.class);
                message = response.getMessage();

                Intent i = new Intent(TouchpointDetails.this, Journey_Visualization.class);
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


}
