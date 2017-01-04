package poc.servicedesigntoolkit.getpost.journey.view;

import android.content.res.Resources;

import journey.dto.JourneyListDTO;

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

    public JourneyListDTO getJourneyListForRegister(JourneyListDTO journeyListDTO){
        return journeyListDTO;
    };
}
