package main.datahandler;

import android.widget.EditText;

import java.util.Observable;
import java.util.Observer;

import common.api.APIContext;
import common.constants.ConstantValues;
import common.dto.RESTResponse;
import journey.activity.JourneyListActivity;
import journey.dto.JourneyFieldResearcherDTO;
import journeyVisualization.Journey_Visualization;
import main.view.MainView;
import touchpoint.activity.TouchPointListActivity;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 1/4/17.
 */

public class DHFieldResearcherSignIn implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        APIContext apiContext = (APIContext) observable;
        if (!MainView.class.toString().equals(apiContext.getAbstractController().getAbstractView().getClass().toString())) {
            return;
        }

        RESTResponse response = (RESTResponse) o;
        MainView mainView = (MainView) apiContext.getAbstractController().getAbstractView();

        JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
        journeyFieldResearcherDTO.setFieldResearcherDTO(new FieldResearcherDTO());
        journeyFieldResearcherDTO.getFieldResearcherDTO().setSdtUserDTO(new SdtUserDTO());
        journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().
                setUsername(((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString());

        if (ConstantValues.REST_MESSAGE_FIELD_RESEARCHER_NOT_REGISTERED.equals(response.getMessage())) {
            apiContext.getAbstractController().forwardToScreen(JourneyListActivity.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
        } else {
            apiContext.getAbstractController().forwardToScreen(Journey_Visualization.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
        }
    }
}
