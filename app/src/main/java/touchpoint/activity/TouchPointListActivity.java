package touchpoint.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import common.action.ActionFactoryProducer;
import common.action.ActionOnBackClick;
import common.api.APIGetTouchPointListOfRegisteredJourney;
import journey.dto.JourneyFieldResearcherDTO;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.view.TouchPointListView;

/**
 * Created by Gunjan Pathak on 11/01/2017.
 */

public class TouchPointListActivity extends AppCompatActivity {
    private TouchPointListView touchPointListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visulaization_journey);


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Trial", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("JourneyFieldResearcherDTO", "");
        Log.d("TouchPointListActivity", " EMPTY "+json);
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = gson.fromJson(json, JourneyFieldResearcherDTO.class);
        touchPointListView = new TouchPointListView(this);
        new APIGetTouchPointListOfRegisteredJourney(journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO(), touchPointListView).execute();

    }

    @Override
    public void onBackPressed() {
        ActionFactoryProducer.getFactory(ActionOnBackClick.class.toString()).initOnBackClickAction(touchPointListView).onBackClick();
    }
}
