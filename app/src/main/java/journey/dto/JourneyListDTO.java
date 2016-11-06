package journey.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longnguyen on 11/6/16.
 */

public class JourneyListDTO {
    private List<JourneyDTO> journeyDTOList = new ArrayList<>();

    public List<JourneyDTO> getJourneyDTOList() {
        return journeyDTOList;
    }

    public void setJourneyDTOList(List<JourneyDTO> journeyDTOList) {
        this.journeyDTOList = journeyDTOList;
    }
}
