package journey.controller;

import common.api.APIDataHandler;
import common.constants.APIUrl;
import common.api.APIGateway;
import common.api.APICaller;
import journey.dto.JourneyListDTO;

/**
 * Created by longnguyen on 12/29/16.
 */

public class JourneyController extends APICaller {
    public JourneyController(APIDataHandler apiDataHandler) {
        super(apiDataHandler);
    }

    public void getJourneyList() {
        getApiGateway().setMethod(APIUrl.METHOD_GET);
        getApiGateway().setOutputClass(JourneyListDTO.class);
        getApiGateway().setUrl(APIUrl.API_GET_JOURNEY_LIST_FOR_REGISTER);
        getApiGateway().execute();
    }
}
