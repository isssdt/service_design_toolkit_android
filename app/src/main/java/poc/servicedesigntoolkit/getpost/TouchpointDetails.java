package poc.servicedesigntoolkit.getpost;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Gunjan Pathak on 06-Oct-16.
 */

public class TouchpointDetails extends AppCompatActivity {

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

        touchpointName.setText(Touchpoint);

        touchpointName.setKeyListener(null);
        touchpointName.setFocusable(false);

        action.setKeyListener(null);
        action.setFocusable(false);

        channel.setKeyListener(null);
        channel.setFocusable(false);

        touchpointDescription.setKeyListener(null);
        touchpointDescription.setFocusable(false);

    }

}
