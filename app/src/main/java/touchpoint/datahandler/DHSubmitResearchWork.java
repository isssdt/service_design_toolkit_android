package touchpoint.datahandler;

import java.util.Observable;
import java.util.Observer;

import common.api.APIContext;
import common.constants.ConstantValues;
import common.dto.RESTResponse;
import journey.activity.JourneyListActivity;
import journey.dto.JourneyFieldResearcherDTO;
import touchpoint.activity.TouchPointListActivity;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.view.TouchPointDetailsView;

/**
 * Created by longnguyen on 1/1/17.
 */

public class DHSubmitResearchWork implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        APIContext apiContext = (APIContext)observable;
        if (!TouchPointDetailsView.class.toString().equals(apiContext.getControllerActionListener().getViewScreen().getClass().toString())) {
            return;
        }

        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO)apiContext.getControllerActionListener().getViewScreen().
                getContext().getIntent().getExtras().get(ConstantValues.BUNDLE_KEY_TOUCH_POINT_FIELD_RESEARCHER_DTO);
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
        journeyFieldResearcherDTO.setFieldResearcherDTO(touchPointFieldResearcherDTO.getFieldResearcherDTO());
        journeyFieldResearcherDTO.setJourneyDTO(touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO());

        if (ConstantValues.REST_MESSAGE_RESPONSE_UPDATE_SUCCESSFUL.equals(((RESTResponse)o).getMessage())) {
            apiContext.getControllerActionListener().forwardToScreen(TouchPointListActivity.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
        } else {
            apiContext.getControllerActionListener().forwardToScreen(JourneyListActivity.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
        }
    }
}
