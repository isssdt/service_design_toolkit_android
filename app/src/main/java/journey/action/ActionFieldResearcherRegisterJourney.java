package journey.action;

import android.view.View;
import android.widget.Toast;

import java.util.Map;
import java.util.Observable;

import common.actionlistener.ViewController;
import common.actionlistener.ViewActionCommand;
import common.api.APIContext;
import common.constants.ConstantValues;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.view.JourneyListView;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 1/1/17.
 */

public class ActionFieldResearcherRegisterJourney implements ViewActionCommand {
    @Override
    public boolean actionValidation(ViewController controllerSomePattern) {
        return false;
    }

    @Override
    public void update(Observable observable, Object o) {
        ViewController controllerActionListener = (ViewController)observable;
        Map<String, Object> updateObjects = (Map<String, Object>) o;

        if (((View)updateObjects.get(ConstantValues.ACTION_LISTENER_VIEW_KEY)).getId() != R.id.listView) {
            return;
        }


        JourneyListView journeyListView = (JourneyListView)controllerActionListener.getViewScreen();
        String journeyName = (String) journeyListView.getListView().getItemAtPosition((Integer)updateObjects.get(ConstantValues.ACTION_LISTENER_JOURNEY_SELECTED_POSITION_KEY));

        Toast.makeText(journeyListView.getContext().getApplicationContext(), "Registered for : " + journeyName + " journey.", Toast.LENGTH_SHORT).show();

        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName(journeyName);

        JourneyFieldResearcherDTO journeyFieldResearcherDTO =
                (JourneyFieldResearcherDTO)controllerActionListener.getViewScreen().getContext().getIntent().getExtras().get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO);
        journeyFieldResearcherDTO.setJourneyDTO(journeyDTO);

        new APIContext(controllerActionListener).registerFieldResearcherWithJourney(journeyFieldResearcherDTO);
    }
}
