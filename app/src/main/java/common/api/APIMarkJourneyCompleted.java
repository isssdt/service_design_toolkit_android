package common.api;

import android.content.Intent;

import common.constants.APIUrl;
import common.dto.RESTResponse;
import common.view.AbstractView;
import journey.dto.JourneyFieldResearcherDTO;
import journeyemotion.emotionMeter;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class APIMarkJourneyCompleted extends APIFacade<RESTResponse, SdtUserDTO> implements APIExecutor<RESTResponse> {
    public APIMarkJourneyCompleted(SdtUserDTO input, AbstractView view) {
        super(APIUrl.API_MARK_JOURNEY_COMPLETED, RESTResponse.class, input, APIUrl.METHOD_POST, view);
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(RESTResponse data) {
        Intent i = new Intent(view.getContext(), emotionMeter.class);
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO) view.getContext().getIntent().getExtras().get(JourneyFieldResearcherDTO.class.toString());
        i.putExtra("Username", journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        i.putExtra("JourneyName", journeyFieldResearcherDTO.getJourneyDTO().getJourneyName());
        i.putExtra("Message", data.getMessage());
        view.getContext().startActivity(i);
    }
}
