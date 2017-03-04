package touchpoint.action;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import common.action.BaseAction;
import common.api.APIMarkJourneyCompleted;
import common.constants.ConstantValues;
import common.view.AbstractView;
import journey.dto.JourneyFieldResearcherDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class ACTION_BUTTON_TOUCH_POINT_LIST_SUBMIT_JOURNEY extends BaseAction implements View.OnClickListener {
    public ACTION_BUTTON_TOUCH_POINT_LIST_SUBMIT_JOURNEY(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder adb = new AlertDialog.Builder(abstractView.getContext());
        adb.setTitle("Submit Journey");
        adb.setMessage(" Thank you for your response.Please proceed for new journey registeration");
        adb.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle extras = abstractView.getContext().getIntent().getExtras();
                JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO);
                new APIMarkJourneyCompleted(journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO(), abstractView).execute();
            }
        });
        adb.setNegativeButton("Cancel", null);
        adb.show();
    }

    @Override
    public boolean validation(View view) {
        return false;
    }
}
