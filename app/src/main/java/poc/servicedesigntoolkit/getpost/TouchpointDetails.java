package poc.servicedesigntoolkit.getpost;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import common.dto.RESTResponse;
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
    private static final String COMPLETE_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/journey_mark_complete";
    private static final String completed = "Please informed that you have completed work for all Touch Points";
    private static final String completed_confirmation = "Journey has been marked as Completed";
    EditText touchpointName_edit, channelDescription_edit, channel_edit, action_edit, comment_edit, reaction_edit;
    RatingBar ratingBar;
    TextView image;
    Button submit, reset;
    ImageButton photo;
    String touchpoint, username, reaction, comment, JourneyName;
    String name, action, channel_desc, channel;
    Integer id;
    String id_String, rating_string;
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

        setTitle(touchpoint);

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

        } else if (v == photo) {
            Intent i = new Intent(TouchpointDetails.this, SelectPhoto.class);
            startActivity(i);
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
        reaction = reaction_edit.getText().toString();
        comment = comment_edit.getText().toString();
        rating = (int) ratingBar.getRating();
        rating_string = Integer.toString(rating);
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, RESTResponse> {
        @Override
        protected RESTResponse doInBackground(Void... params) {
            try {
                final String url = "http://54.169.59.1:9090/service_design_toolkit-web/api/update_research_work";
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
                touchPointFieldResearcherDTO.setComments(comment);
                touchPointFieldResearcherDTO.setReaction(reaction);

                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setValue(rating_string);
                touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);

                RESTResponse response =
                        restTemplate.postForObject(url, touchPointFieldResearcherDTO, RESTResponse.class);
                message = response.getMessage();

                Intent i = new Intent(TouchpointDetails.this, TouchPointListActivity.class);
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
            reaction = reaction_edit.getText().toString();
            comment = comment_edit.getText().toString();
            rating = (int) ratingBar.getRating();
            rating_string = Integer.toString(rating);
        }

        @Override
        protected void onPostExecute(RESTResponse response) {

        }
    }


}
