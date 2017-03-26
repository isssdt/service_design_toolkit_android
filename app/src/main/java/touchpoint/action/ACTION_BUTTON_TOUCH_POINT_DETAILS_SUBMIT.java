package touchpoint.action;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import common.action.BaseAction;
import common.api.APIGetTouchPointListOfRegisteredJourney;
import common.api.APIUpdateResearchWork;
import common.constants.ConstantValues;
import common.dto.MasterDataDTO;
import common.view.AbstractView;
import connectionStatus.AppStatus;
import touchpoint.activity.TouchPointDetailsActivity;
import touchpoint.activity.TouchPointListActivity;
import touchpoint.dto.RatingDTO;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.view.TouchPointDetailsView;

/**
 * Created by longnguyen on 3/5/17.
 */

public class ACTION_BUTTON_TOUCH_POINT_DETAILS_SUBMIT extends BaseAction implements View.OnClickListener {

    public ACTION_BUTTON_TOUCH_POINT_DETAILS_SUBMIT(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public void onClick(View view) {
        if (AppStatus.getInstance(view.getContext()).isOnline()) {

            //Snackbar.make(view, "You are online!!!!", Snackbar.LENGTH_LONG).show();
            Bundle bundle = abstractView.getContext().getIntent().getExtras();
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) bundle.get(TouchPointFieldResearcherDTO.class.toString());
        if (!validation(view)) {
            return;
        }
        touchPointFieldResearcherDTO.setReaction(((EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_REACTION)).getText().toString());
        touchPointFieldResearcherDTO.setComments(((EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_COMMENT)).getText().toString());
        touchPointFieldResearcherDTO.setRatingDTO(new RatingDTO());
        touchPointFieldResearcherDTO.getRatingDTO().setValue(
                String.valueOf((int) ((RatingBar) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_RATING_BAR)).getRating()));
        touchPointFieldResearcherDTO.setDuration(
                Integer.valueOf(((EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_ACTUAL_DURATION)).getText().toString()));
        touchPointFieldResearcherDTO.setDurationUnitDTO(new MasterDataDTO());
        touchPointFieldResearcherDTO.getDurationUnitDTO().setDataValue(
                ((Spinner) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_SPINNER_ACTUAL_DURATION_UNIT)).getSelectedItem().toString());
        new APIUpdateResearchWork(touchPointFieldResearcherDTO, abstractView).execute();
            APIGetTouchPointListOfRegisteredJourney remove =new APIGetTouchPointListOfRegisteredJourney(touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO(), abstractView);
            remove.removeGeofencesButtonHandler();
        } else {

            Snackbar.make(view,"Please check Your Internet Connection!!!!",Snackbar.LENGTH_LONG).show();
            Log.v("Home", "############################You are not online!!!!");
        }

    }

    @Override
    public boolean validation(View view) {
        EditText reaction = (EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_REACTION);
        EditText actualDuration = (EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_ACTUAL_DURATION);
        RatingBar ratingBar = (RatingBar) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_RATING_BAR);
        if (reaction.getText().length() == 0 && ((int) ratingBar.getRating()) == 0.0) {
            Toast.makeText(abstractView.getContext(), "Please enter the Rating and Reaction", Toast.LENGTH_SHORT).show();
            return false;
        } else if (((int) ratingBar.getRating()) == 0.0) {
            Toast.makeText(abstractView.getContext(), "Please enter the Rating", Toast.LENGTH_SHORT).show();
            return false;
        } else if (reaction.getText().length() == 0) {
            reaction.setError("Please Enter what did you do");
           // Toast.makeText(abstractView.getContext(), "Please enter what did you do", Toast.LENGTH_SHORT).show();
            return false;
        } else if (actualDuration.getText().length() == 0) {
            reaction.setError("Please Enter the Time Taken");
            //Toast.makeText(abstractView.getContext(), "Please enter the Time Taken", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
