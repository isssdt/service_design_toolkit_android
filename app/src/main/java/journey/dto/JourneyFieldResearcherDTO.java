package journey.dto;

import user.dto.FieldResearcherDTO;

/**
 * Created by longnguyen on 11/6/16.
 */

public class JourneyFieldResearcherDTO {
    private JourneyDTO journeyDTO;
    private FieldResearcherDTO fieldResearcherDTO;
    private String status;

    public JourneyDTO getJourneyDTO() {
        return journeyDTO;
    }

    public void setJourneyDTO(JourneyDTO journeyDTO) {
        this.journeyDTO = journeyDTO;
    }

    public FieldResearcherDTO getFieldResearcherDTO() {
        return fieldResearcherDTO;
    }

    public void setFieldResearcherDTO(FieldResearcherDTO fieldResearcherDTO) {
        this.fieldResearcherDTO = fieldResearcherDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
