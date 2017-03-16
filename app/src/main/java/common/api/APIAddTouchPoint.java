package common.api;

import common.constants.APIUrl;
import common.dto.RESTResponse;
import common.view.AbstractView;
import touchpoint.dto.TouchPointFieldResearcherDTO;

/**
 * Created by longnguyen on 3/16/17.
 */

public class APIAddTouchPoint extends APIFacade<RESTResponse, TouchPointFieldResearcherDTO> implements APIExecutor<RESTResponse> {
    public APIAddTouchPoint(TouchPointFieldResearcherDTO input, AbstractView view) {
        super(APIUrl.API_ADD_TOUCHPOINT, RESTResponse.class, input, APIUrl.METHOD_POST, view);
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(RESTResponse data) {

    }
}
