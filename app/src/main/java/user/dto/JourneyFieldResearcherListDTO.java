package user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

import journey.dto.JourneyFieldResearcherDTO;

/**
 * Created by Gunjan Pathak on 24/01/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JourneyFieldResearcherListDTO implements Serializable {
    private List<JourneyFieldResearcherDTO> journeyFieldResearcherDTOList;

    public List<JourneyFieldResearcherDTO> getJourneyFieldResearcherDTOList() {
        return journeyFieldResearcherDTOList;
    }

    public void setJourneyFieldResearcherDTOList(List<JourneyFieldResearcherDTO> journeyFieldResearcherDTOList) {
        this.journeyFieldResearcherDTOList = journeyFieldResearcherDTOList;
    }
}
