package common.api;

import android.content.Intent;

import common.constants.APIUrl;
import common.dto.RESTResponse;
import common.view.AbstractView;
import journeyemotion.emotionMeter;
import poc.servicedesigntoolkit.getpost.MainActivity;
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
        i.putExtra("Message", data.getMessage());
        view.getContext().startActivity(i);
    }
}
