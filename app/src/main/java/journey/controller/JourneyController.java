package journey.controller;

import android.content.res.Resources;

import journey.api.JourneyAPI;
import journey.dto.JourneyDTO;

/**
 * Created by dingyi on 12/12/16.
 */

public class JourneyController {
    private JourneyAPI journeyAPI;

    public JourneyController() {
        journeyAPI = new JourneyAPI();
    }

    public JourneyDTO getJourneyByName(JourneyDTO journeyDTO) {
        try {
            JourneyDTO result = null;
            result = journeyAPI.APIGetJourneyByName(journeyDTO);
            return result;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
