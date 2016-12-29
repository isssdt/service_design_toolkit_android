package journey.api;

import journey.dto.JourneyListDTO;

/**
 * Created by longnguyen on 12/29/16.
 */

public interface HttpJSONGetJourneyListTaskCaller {
    void onHttpGetTaskSucceeded(JourneyListDTO journeyListDTO);
}
