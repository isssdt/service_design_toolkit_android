package touchpoint.activity;

/**
 * Created by Gunjan Pathak on 28-Oct-16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import common.api.APIContext;
import common.constants.ConstantValues;
import journey.dto.JourneyFieldResearcherDTO;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.controller.TouchPointController;
import touchpoint.view.TouchPointListView;

public class TouchPointListActivity extends AppCompatActivity {
    private TouchPointController touchPointController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpoint_recycle);

        touchPointController = new TouchPointController(new TouchPointListView(this));

        JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO)getIntent().getExtras().get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO);
        new APIContext(touchPointController).getListOfTouchPointForRegisteredJourney(journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO());

    }
}