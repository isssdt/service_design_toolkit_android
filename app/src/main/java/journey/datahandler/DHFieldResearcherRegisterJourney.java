package journey.datahandler;

import java.util.Observable;
import java.util.Observer;

import common.api.APIContext;
import common.constants.ConstantValues;
import journey.dto.JourneyFieldResearcherDTO;
import journey.view.JourneyListView;
import touchpoint.activity.TouchPointListActivity;

/**
 * Created by longnguyen on 12/31/16.
 */

public class DHFieldResearcherRegisterJourney implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        APIContext apiContext = (APIContext)observable;
        if (!JourneyListView.class.toString().equals(apiContext.getControllerActionListener().getViewScreen().getClass().toString())) {
            return;
        }

        apiContext.getControllerActionListener().forwardToScreen(TouchPointListActivity.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, (JourneyFieldResearcherDTO)o);
    }
}
