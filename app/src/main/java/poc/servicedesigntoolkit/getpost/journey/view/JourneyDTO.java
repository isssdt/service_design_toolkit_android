package poc.servicedesigntoolkit.getpost.journey.view;

/**
 * Created by longnguyen on 11/6/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

import user.dto.JourneyFieldResearcherListDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JourneyDTO implements Serializable {

    private String journeyName;
    private Integer noOfFieldResearcher;
    private Character isActive;
    private Date startDate;
    private Date endDate;
    private Character canBeRegistered;
    private String description;
    private Character isSequence;
    private JourneyFieldResearcherListDTO journeyFieldResearcherListDTO;

    public JourneyDTO() {
    }

    public JourneyDTO(String journeyName, Integer noOfFieldResearcher, Character isActive, Date startDate, Date endDate, Character canBeRegistered, String description) {
        this.journeyName = journeyName;
        this.noOfFieldResearcher = noOfFieldResearcher;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
        this.canBeRegistered = canBeRegistered;
        this.description = description;
    }

    public Character getIsSequence() {
        return isSequence;
    }

    public void setIsSequence(Character isSequence) {
        this.isSequence = isSequence;
    }

    public JourneyFieldResearcherListDTO getJourneyFieldResearcherListDTO() {
        return journeyFieldResearcherListDTO;
    }

    public void setJourneyFieldResearcherListDTO(JourneyFieldResearcherListDTO journeyFieldResearcherListDTO) {
        this.journeyFieldResearcherListDTO = journeyFieldResearcherListDTO;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getCanBeRegistered() {
        return canBeRegistered;
    }

    public void setCanBeRegistered(Character canBeRegistered) {
        this.canBeRegistered = canBeRegistered;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public Integer getNoOfFieldResearcher() {
        return noOfFieldResearcher;
    }

    public void setNoOfFieldResearcher(Integer noOfFieldResearcher) {
        this.noOfFieldResearcher = noOfFieldResearcher;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(Character isActive) {
        this.isActive = isActive;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object obj) {
        return journeyName.equals(((JourneyDTO)obj).getJourneyName());
    }
}
