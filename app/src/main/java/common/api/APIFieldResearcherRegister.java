package common.api;

import android.widget.EditText;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.dto.RESTResponse;
import common.utils.Utils;
import common.view.AbstractView;
import journey.activity.JourneyListActivity;
import journey.dto.JourneyFieldResearcherDTO;
import journeyVisualization.Journey_Visualization;
import main.view.MainView;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 3/2/17.
 */

public class APIFieldResearcherRegister extends APIFacade<RESTResponse, SdtUserDTO> implements APIExecutor<RESTResponse> {
    public APIFieldResearcherRegister(SdtUserDTO sdtUserDTO, AbstractView view) {
        super(APIUrl.API_FIELD_RESEARCHER_REGISTER, RESTResponse.class, sdtUserDTO, APIUrl.METHOD_POST, view);
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(RESTResponse data) {
        MainView mainView = (MainView) this.view;

        JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
        journeyFieldResearcherDTO.setFieldResearcherDTO(new FieldResearcherDTO());
        journeyFieldResearcherDTO.getFieldResearcherDTO().setSdtUserDTO(new SdtUserDTO());
        journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().
                setUsername(((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString());

        if (ConstantValues.REST_MESSAGE_FIELD_RESEARCHER_NOT_REGISTERED.equals(data.getMessage())) {
            Utils.forwardToScreen(mainView.getContext(), JourneyListActivity.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
        } else {
            Utils.forwardToScreen(mainView.getContext(), Journey_Visualization.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
        }
    }
}
