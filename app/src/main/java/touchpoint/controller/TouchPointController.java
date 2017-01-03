package touchpoint.controller;

import common.api.APIDataHandler;
import common.constants.APIUrl;
import common.api.APIGateway;
import common.api.APICaller;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 12/29/16.
 */

public class TouchPointController extends APICaller {
    public TouchPointController(APIDataHandler apiDataHandler) {
        super(apiDataHandler);
    }

    public void getListOfTouchPointForRegisteredJourney(SdtUserDTO sdtUserDTO) {
        getApiGateway().setUrl(APIUrl.API_GET_TOUCH_POINT_LIST_OF_REGISTER_JOURNEY);
        getApiGateway().setOutputClass(TouchPointFieldResearcherListDTO.class);
        getApiGateway().setMethod(APIUrl.METHOD_POST);
        getApiGateway().setInput(sdtUserDTO);
        getApiGateway().execute();
    }
}
