package journey.controller;

import android.content.res.Resources;

import journey.api.JourneyAPI;
import journey.dto.JourneyDTO;

/**
 * Created by dingyi on 12/12/16.
 */

public class JourneyController {
    public JourneyDTO getJourneyByName(JourneyDTO journeyDTO) {
        try {
            JourneyDTO result = null;
            JourneyAPI journeyAPI = null;
            result = journeyAPI.APIGetJourneyByName(journeyDTO);
            return result;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
