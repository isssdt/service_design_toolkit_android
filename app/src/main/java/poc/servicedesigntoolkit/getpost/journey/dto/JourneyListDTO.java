package poc.servicedesigntoolkit.getpost.journey.dto;

import java.util.ArrayList;
import java.util.List;

import poc.servicedesigntoolkit.getpost.journey.view.JourneyDTO;

/**
 * Created by longnguyen on 11/6/16.
 */


public class JourneyListDTO {

    public JourneyListDTO(){}
    private List<JourneyDTO> journeyDTOList = new ArrayList<>();

    public JourneyListDTO(List<JourneyDTO> journeyDTOList) {
        this.journeyDTOList = journeyDTOList;
    }

    public List<JourneyDTO> getJourneyDTOList() {
        return journeyDTOList;
    }

    public void setJourneyDTOList(List<JourneyDTO> journeyDTOList) {

        this.journeyDTOList = journeyDTOList;
    }
}
