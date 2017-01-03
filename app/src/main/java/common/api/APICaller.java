package common.api;

//import journey.dto.JourneyListDTO;

/**
 * Created by longnguyen on 12/29/16.
 */

public abstract class APICaller {
    private APIDataHandler apiDataHandler;
    private APIGateway apiGateway;

    public APICaller() {
    }

    public APICaller(APIDataHandler apiDataHandler) {
        this.apiDataHandler = apiDataHandler;
        apiGateway = new APIGateway(this);
    }

    public void onAPICallSucceeded(Object outputData) {
        apiDataHandler.handleData(outputData);
    }

    public APIGateway getApiGateway() {
        return apiGateway;
    }
}
