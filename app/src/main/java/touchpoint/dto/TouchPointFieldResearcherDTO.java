package touchpoint.dto;

/**
 * Created by longnguyen on 11/6/16.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TouchPointFieldResearcherDTO {
    private TouchPointDTO touchpointDTO;

    public TouchPointDTO getTouchpointDTO() {
        return touchpointDTO;
    }

    public void setTouchpointDTO(TouchPointDTO touchpointDTO) {
        this.touchpointDTO = touchpointDTO;
    }
}
