package main.datahandler;

import java.util.Observable;
import java.util.Observer;

import common.api.APIContext;
import common.constants.ConstantValues;
import common.dto.RESTResponse;
import journey.activity.JourneyListActivity;
import journey.dto.JourneyFieldResearcherDTO;
import main.view.MainView;
import touchpoint.activity.TouchPointListActivity;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 1/1/17.
 */

public class DHFieldResearcherSignIn implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        APIContext apiContext = (APIContext)observable;
        if (!MainView.class.toString().equals(apiContext.getControllerActionListener().getViewScreen().getClass().toString())) {
            return;
        }

        RESTResponse response = (RESTResponse)o;
        MainView mainView = (MainView)apiContext.getControllerActionListener().getViewScreen();

        SdtUserDTO sdtUserDTO = new SdtUserDTO();
        sdtUserDTO.setUsername(mainView.getUsername().getText().toString());
        FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);

        JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
        journeyFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);

        if (ConstantValues.REST_MESSAGE_FIELD_RESEARCHER_NOT_REGISTERED.equals(response.getMessage())) {
            apiContext.getControllerActionListener().forwardToScreen(JourneyListActivity.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
        } else {
            apiContext.getControllerActionListener().forwardToScreen(TouchPointListActivity.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
        }
    }
}
