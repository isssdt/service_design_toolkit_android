package journey.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import poc.servicedesigntoolkit.getpost.journey.view.JourneyDTO;

/**
 * Created by longnguyen on 11/6/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class JourneyListDTO implements Serializable {

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
