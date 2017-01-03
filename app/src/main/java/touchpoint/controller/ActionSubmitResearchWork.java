package touchpoint.controller;

import android.view.View;
import android.widget.Toast;

import java.util.Map;
import java.util.Observable;

import common.actionlistener.ViewActionCommand;
import common.actionlistener.ViewController;
import common.api.APIContext;
import common.constants.ConstantValues;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.dto.RatingDTO;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.view.TouchPointDetailsView;

/**
 * Created by longnguyen on 1/1/17.
 */

public class ActionSubmitResearchWork implements ViewActionCommand {
    @Override
    public boolean actionValidation(ViewController controllerActionListener) {
        TouchPointDetailsView touchPointDetailsView = (TouchPointDetailsView)controllerActionListener.getViewScreen();
        if (touchPointDetailsView.getET_reaction().getText().length() == 0 && ((int) touchPointDetailsView.getRB_rating().getRating()) == 0.0) {
            Toast.makeText(touchPointDetailsView.getContext(), "Please enter the Rating and Reaction", Toast.LENGTH_SHORT).show();
            return false;
        } else if (((int) touchPointDetailsView.getRB_rating().getRating()) == 0.0) {
            Toast.makeText(touchPointDetailsView.getContext(), "Please enter the Rating", Toast.LENGTH_SHORT).show();
            return false;
        } else if (touchPointDetailsView.getET_reaction().getText().length() == 0) {
            Toast.makeText(touchPointDetailsView.getContext(), "Please enter the Reaction", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void update(Observable observable, Object o) {
        Map<String, Object> updateObjects = (Map<String, Object>)o;
        if (((View)updateObjects.get(ConstantValues.ACTION_LISTENER_VIEW_KEY)).getId() != R.id.submit) {
            return;
        }

        ViewController controllerActionListener = (ViewController)observable;
        if (!actionValidation(controllerActionListener)) {
            return;
        }

        TouchPointDetailsView touchPointDetailsView = (TouchPointDetailsView)controllerActionListener.getViewScreen();

        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO)touchPointDetailsView.getContext().
                getIntent().getExtras().get(ConstantValues.BUNDLE_KEY_TOUCH_POINT_FIELD_RESEARCHER_DTO);

        touchPointFieldResearcherDTO.setReaction(touchPointDetailsView.getET_reaction().getText().toString());
        touchPointFieldResearcherDTO.setComments(touchPointDetailsView.getET_comment().getText().toString());

        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setValue(String.valueOf((int)touchPointDetailsView.getRB_rating().getRating()));
        touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);

        new APIContext(controllerActionListener).APISubmitResearchWork(touchPointFieldResearcherDTO);
    }
}
