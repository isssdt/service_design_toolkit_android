package touchpoint.dto;

/**
 * Created by longnguyen on 11/6/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import user.dto.FieldResearcherDTO;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TouchPointFieldResearcherDTO {
    private FieldResearcherDTO fieldResearcherDTO;
    private TouchPointDTO touchpointDTO;
    private String comments;
    private String reaction;
    private RatingDTO ratingDTO;
    private String status;

    public FieldResearcherDTO getFieldResearcherDTO() {
        return fieldResearcherDTO;
    }

    public void setFieldResearcherDTO(FieldResearcherDTO fieldResearcherDTO) {
        this.fieldResearcherDTO = fieldResearcherDTO;
    }

    public TouchPointDTO getTouchpointDTO() {
        return touchpointDTO;
    }

    public void setTouchpointDTO(TouchPointDTO touchpointDTO) {
        this.touchpointDTO = touchpointDTO;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public RatingDTO getRatingDTO() {
        return ratingDTO;
    }

    public void setRatingDTO(RatingDTO ratingDTO) {
        this.ratingDTO = ratingDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
