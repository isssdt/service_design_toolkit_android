package common.api;

import java.util.Observable;

import common.actionlistener.ViewController;
import main.datahandler.DHFieldResearcherSignIn;
import common.api.handler.GetTouchPointListOfRegisteredJourney;
import journey.datahandler.DHFieldResearcherRegisterJourney;
import common.constants.APIUrl;
import common.dto.RESTResponse;
import journey.controller.GetJourneyListForRegister;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;
import touchpoint.datahandler.DHSubmitResearchWork;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 12/29/16.
 */

public class APIContext extends Observable implements APICaller {
    private ViewController controllerActionListener;

    public APIContext(ViewController controllerActionListener) {
        this.controllerActionListener = controllerActionListener;
    }

    public ViewController getControllerActionListener() {
        return controllerActionListener;
    }

    @Override
    public void onAPICallSucceeded(Object outputData) {
        setChanged();
        notifyObservers(outputData);
    }

    public void registerFieldResearcher(SdtUserDTO sdtUserDTO) {
        addObserver(new DHFieldResearcherSignIn());
        new APIGateway(this, APIUrl.API_FIELD_RESEARCHER_REGISTER, RESTResponse.class, sdtUserDTO, APIUrl.METHOD_POST).execute();
    }

    public void getJourneyList() {
        addObserver(new GetJourneyListForRegister());
        new APIGateway(this, APIUrl.API_GET_JOURNEY_LIST_FOR_REGISTER, JourneyListDTO.class, null, APIUrl.METHOD_GET).execute();
    }

    public void registerFieldResearcherWithJourney(JourneyFieldResearcherDTO journeyFieldResearcherDTO) {
        addObserver(new DHFieldResearcherRegisterJourney());
        new APIGateway(this, APIUrl.API_REGISTER_FIELD_RESEARCHER_WITH_JOURNEY, JourneyFieldResearcherDTO.class, journeyFieldResearcherDTO, APIUrl.METHOD_POST).execute();
    }

    public void getListOfTouchPointForRegisteredJourney(SdtUserDTO sdtUserDTO) {
        addObserver(new GetTouchPointListOfRegisteredJourney());
        new APIGateway(this, APIUrl.API_GET_TOUCH_POINT_LIST_OF_REGISTERED_JOURNEY, TouchPointFieldResearcherListDTO.class, sdtUserDTO, APIUrl.METHOD_POST).execute();
    }

    public void APISubmitResearchWork(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) {
        addObserver(new DHSubmitResearchWork());
        new APIGateway(this, APIUrl.API_UPDATE_RESEARCH_WORK, RESTResponse.class, touchPointFieldResearcherDTO, APIUrl.METHOD_POST).execute();
    }
}
