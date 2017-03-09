package touchpoint.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import common.action.ActionFactoryProducer;
import common.action.ActionOnBackClick;
import common.api.APIGetTouchPointListOfRegisteredJourney;
import common.constants.ConstantValues;
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

        Bundle extras = getIntent().getExtras();
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO);

        touchPointListView = new TouchPointListView(this);
        new APIGetTouchPointListOfRegisteredJourney(journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO(), touchPointListView).execute();
    }

    @Override
    public void onBackPressed() {
        ActionFactoryProducer.getFactory(ActionOnBackClick.class.toString()).initOnBackClickAction(touchPointListView).onBackClick();
    }
}
