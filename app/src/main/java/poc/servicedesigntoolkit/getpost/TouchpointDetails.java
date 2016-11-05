package poc.servicedesigntoolkit.getpost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Gunjan Pathak on 06-Oct-16.
 */

public class TouchpointDetails extends AppCompatActivity implements View.OnClickListener {

    EditText touchpointName_edit,channelDescription_edit,channel_edit,action_edit,comment_edit,reaction_edit;
    RatingBar ratingBar;
    TextView image;
    Button submit, reset,photo;

    String touchpoint ,username, reaction, comment;
    String name ,action, channel_desc, channel;
    int rating;

    private static final String TOUCHPOINT_DETAILS_URL = "http://54.169.59.1:9090/service_design_toolkit-web/api/update_research_work";

    private static final String TAG_FIELDRESEARCHERDTO = "fieldResearcherDTO";
    private static final String TAG_SDTUSERDTO = "sdtUserDTO";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_TOUCHPOINTDTO = "touchpointDTO";
    private static final String TAG_TOUCHPOINTDESC = "touchPointDesc";
    private static final String TAG_ID = "id";
    private static final String TAG_COMMENTS = "comments";
    private static final String TAG_REACTION = "reaction";
    private static final String TAG_RATINGDTO = "ratingDTO";
    private static final String TAG_VALUE = "value";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpoint_details);

        Bundle extras = getIntent().getExtras();

        touchpoint = (String) extras.get("Touchpoint");
        username = (String) extras.get("Username");
        action = (String) extras.get("Action");
        channel = (String) extras.get("Channel");
        channel_desc = (String) extras.get("Channel_Desc");
        name = (String) extras.get("Name");

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
        photo = (Button) findViewById(R.id.photo);

        submit.setOnClickListener(this);
        reset.setOnClickListener(this);
        photo.setOnClickListener(this);

        setText();


    }

    public void setText (){

        touchpointName_edit.setText(name);
        channel_edit.setText(channel);
        action_edit.setText(action);
        channelDescription_edit.setText(channel_desc);

    }

    @Override
    public void onClick(View v) {

        if (v == submit) {
            if(validate()){
                getdetails();
                touchpointDetails();
                onBackPressed();
            }
        } else if ( v == reset){

        }else if ( v == photo){
            Intent i = new Intent(TouchpointDetails.this,SelectPhoto.class);
            startActivity(i);
        }
    }

    private boolean validate() {
        if(reaction_edit.getText().length() == 0 && ((int)ratingBar.getRating()) == 0.0){
            Toast.makeText(TouchpointDetails.this,"Please enter the Rating and Reaction",Toast.LENGTH_SHORT).show();
            return false;
        }else if(((int)ratingBar.getRating()) == 0.0){
            Toast.makeText(TouchpointDetails.this,"Please enter the Rating",Toast.LENGTH_SHORT).show();
            return false;
        }else if(reaction_edit.getText().length() == 0){
            Toast.makeText(TouchpointDetails.this,"Please enter the Reaction",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getdetails() {
        reaction = reaction_edit.getText().toString();
        comment = comment_edit.getText().toString();
        rating = (int) ratingBar.getRating();
    }

    private void touchpointDetails() {

        final JSONObject request = new JSONObject();
        try {

            JSONObject fieldResearcherDTO = new JSONObject();
            request.put(TAG_FIELDRESEARCHERDTO,fieldResearcherDTO);

            JSONObject sdtUserDTO = new JSONObject();
            fieldResearcherDTO.put(TAG_SDTUSERDTO,sdtUserDTO);

            sdtUserDTO.put(TAG_USERNAME,"Gunjan");//username

            JSONObject touchpointDTO = new JSONObject();
            request.put(TAG_TOUCHPOINTDTO,touchpointDTO);

            touchpointDTO.put(TAG_TOUCHPOINTDESC,"p1");//touchpoint

            touchpointDTO.put(TAG_ID,"49");

            request.put(TAG_COMMENTS,"FROM APP COMMENT");//comment

            request.put(TAG_REACTION,"FROM APP REACTION");//reaction

            JSONObject ratingDTO = new JSONObject();
            request.put(TAG_RATINGDTO,ratingDTO);

            ratingDTO.put(TAG_VALUE,4);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Touchpoint Request", request.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                TOUCHPOINT_DETAILS_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                Toast.makeText(getApplicationContext(),"Touchpoint is added into Database.",Toast.LENGTH_SHORT);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


}
