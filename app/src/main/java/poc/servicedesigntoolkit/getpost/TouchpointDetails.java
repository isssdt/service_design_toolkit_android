package poc.servicedesigntoolkit.getpost;

import android.app.Activity;
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

    EditText touchpointName,touchpointDescription,channel,action,comment_edit,reaction_edit;
    RatingBar ratingBar;
    TextView image;
    Button submit, reset,photo,map;

    String touchpoint ,username, reaction, comment;
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
        setTitle(touchpoint);

        touchpointName = (EditText) findViewById(R.id.touchpoint_name);
        channel = (EditText) findViewById(R.id.channel);
        touchpointDescription = (EditText) findViewById(R.id.description);
        action = (EditText) findViewById(R.id.action);
        reaction_edit = (EditText) findViewById(R.id.reaction);
        comment_edit = (EditText) findViewById(R.id.comment);

        image = (TextView) findViewById(R.id.image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        submit = (Button) findViewById(R.id.submit);
        reset = (Button) findViewById(R.id.reset);
        photo = (Button) findViewById(R.id.photo);
        map = (Button) findViewById(R.id.map);

        submit.setOnClickListener(this);
        reset.setOnClickListener(this);
        photo.setOnClickListener(this);
        map.setOnClickListener(this);

        setText();


    }

    public void setText (){

        touchpointName.setText(touchpoint);

    }

    @Override
    public void onClick(View v) {

        if (v == submit) {

            /*
            Intent i = new Intent(TouchpointDetails.this,MapsActivity.class);
            startActivity(i);*/
            getdetails();
            touchpointDetails();
            onBackPressed();
        } else if ( v == reset){

        }else if ( v == photo){
            Intent i = new Intent(TouchpointDetails.this,SelectPhoto.class);
            startActivity(i);

        }else if ( v == map){
            Intent i = new Intent(TouchpointDetails.this,MapsActivity.class);

            startActivity(i);
        }
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

            sdtUserDTO.put("username",username);

            JSONObject touchpointDTO = new JSONObject();
            request.put(TAG_TOUCHPOINTDTO,touchpointDTO);

            touchpointDTO.put(TAG_TOUCHPOINTDESC,touchpoint);

            touchpointDTO.put(TAG_ID,"1");

            request.put(TAG_COMMENTS,comment);

            request.put(TAG_REACTION,reaction);

            JSONObject ratingDTO = new JSONObject();
            request.put(TAG_RATINGDTO,ratingDTO);

            ratingDTO.put(TAG_VALUE,rating);


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
