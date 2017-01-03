package touchpoint.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import poc.servicedesigntoolkit.getpost.R;
import touchpoint.controller.TouchPointController;
import touchpoint.view.TouchPointDetailsView;

/**
 * Created by Gunjan Pathak on 06-Oct-16.
 */

public class TouchPointDetailsActivity extends AppCompatActivity {

    private TouchPointController touchPointController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpoint_details);

        touchPointController = new TouchPointController(new TouchPointDetailsView(this));
    }
}
