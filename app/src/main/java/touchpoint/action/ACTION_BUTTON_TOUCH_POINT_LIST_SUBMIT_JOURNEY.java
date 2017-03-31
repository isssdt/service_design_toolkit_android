package touchpoint.action;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import common.action.BaseAction;
import common.api.APIGetTouchPointListOfRegisteredJourney;
import common.api.APIMarkJourneyCompleted;
import common.view.AbstractView;
import connectionStatus.AppStatus;
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
        if (AppStatus.getInstance(view.getContext()).isOnline()) {
            //Snackbar.make(view, "You are online!!!!", Snackbar.LENGTH_LONG).show();
            AlertDialog.Builder adb = new AlertDialog.Builder(abstractView.getContext());
            adb.setTitle("Submit Journey");
            adb.setMessage(" Thank you for your response.Please proceed for new journey registeration");
            adb.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle extras = abstractView.getContext().getIntent().getExtras();
                    JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO) extras.get(JourneyFieldResearcherDTO.class.toString());
                    new APIMarkJourneyCompleted(journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO(), abstractView).execute();
                    APIGetTouchPointListOfRegisteredJourney remove =new APIGetTouchPointListOfRegisteredJourney(journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO(), abstractView);
//                    remove.removeGeofencesButtonHandler();
                }
            });
            adb.setNegativeButton("Cancel", null);
            adb.show();
        } else {

        Snackbar.make(view,"Please check Your Internet Connection!!!!",Snackbar.LENGTH_LONG).show();
        Log.v("Home", "############################You are not online!!!!");
    }
    }

    @Override
    public boolean validation(View view) {
        return false;
    }
}
