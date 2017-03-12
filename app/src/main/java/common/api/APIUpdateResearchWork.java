package common.api;

import android.os.Bundle;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.dto.RESTResponse;
import common.utils.Utils;
import common.view.AbstractView;
import journey.dto.JourneyFieldResearcherDTO;
import touchpoint.activity.TouchPointListActivity;
import touchpoint.dto.TouchPointFieldResearcherDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class APIUpdateResearchWork extends APIFacade<RESTResponse, TouchPointFieldResearcherDTO> implements APIExecutor<RESTResponse> {
    public APIUpdateResearchWork(TouchPointFieldResearcherDTO input, AbstractView view) {
        super(APIUrl.API_UPDATE_RESEARCH_WORK, RESTResponse.class, input, APIUrl.METHOD_POST, view);
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(RESTResponse data) {
        if (null != this.input.getPhotoLocation() && !this.input.getPhotoLocation().isEmpty()) {
            Utils.uploadImageThread(this.input.getPhotoLocation(),
                    this.input.getTouchpointDTO().getJourneyDTO().getJourneyName() + "_" + this.input.getTouchpointDTO().getTouchPointDesc() + "_" +
                            this.input.getFieldResearcherDTO().getSdtUserDTO().getUsername() + ".jpg");
        }
        Bundle bundle = view.getContext().getIntent().getExtras();
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) bundle.get(TouchPointFieldResearcherDTO.class.toString());
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
        journeyFieldResearcherDTO.setFieldResearcherDTO(touchPointFieldResearcherDTO.getFieldResearcherDTO());
        journeyFieldResearcherDTO.setJourneyDTO(touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO());
        Utils.forwardToScreen(view.getContext(), TouchPointListActivity.class, ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, journeyFieldResearcherDTO);
    }
}
