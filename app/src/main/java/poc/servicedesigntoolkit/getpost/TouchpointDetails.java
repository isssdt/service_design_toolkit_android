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

/**
 * Created by Gunjan Pathak on 06-Oct-16.
 */

public class TouchpointDetails extends AppCompatActivity implements View.OnClickListener {

    EditText touchpointName,touchpointDescription,channel,action,comment,reaction;
    RatingBar ratingBar;
    TextView image;
    Button submit, reset,photo,map;

    String Touchpoint;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpoint_details);

        Bundle extras = getIntent().getExtras();
        Touchpoint = (String) extras.get("Touchpoint");
        setTitle(Touchpoint);

        touchpointName = (EditText) findViewById(R.id.touchpoint_name);
        channel = (EditText) findViewById(R.id.channel);
        touchpointDescription = (EditText) findViewById(R.id.description);
        action = (EditText) findViewById(R.id.action);
        reaction = (EditText) findViewById(R.id.reaction);
        comment = (EditText) findViewById(R.id.comment);

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

        touchpointName.setText(Touchpoint);

    }

    @Override
    public void onClick(View v) {

        if (v == submit) {
            onBackPressed();
            /*
            Intent i = new Intent(TouchpointDetails.this,MapsActivity.class);
            startActivity(i);*/
        } else if ( v == reset){

        }else if ( v == photo){
            Intent i = new Intent(TouchpointDetails.this,SelectPhoto.class);
            startActivity(i);

        }else if ( v == map){
            Intent i = new Intent(TouchpointDetails.this,MapsActivity.class);

            startActivity(i);
        }
    }


}
