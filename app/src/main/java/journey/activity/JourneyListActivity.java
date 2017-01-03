package journey.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import common.api.APIContext;
import journey.controller.JourneyController;
import journey.view.JourneyListView;
import poc.servicedesigntoolkit.getpost.R;

public class JourneyListActivity extends AppCompatActivity {
    private JourneyController journeyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);

        journeyController = new JourneyController(new JourneyListView(this));
        new APIContext(journeyController).getJourneyList();
    }
}
