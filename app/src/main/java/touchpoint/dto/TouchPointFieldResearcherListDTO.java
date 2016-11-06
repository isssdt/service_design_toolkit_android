package touchpoint.dto;

/**
 * Created by longnguyen on 11/6/16.
 */

import java.util.ArrayList;
import java.util.List;

public class TouchPointFieldResearcherListDTO {
    private List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList = new ArrayList<>();

    public List<TouchPointFieldResearcherDTO> getTouchPointFieldResearcherDTOList() {
        return touchPointFieldResearcherDTOList;
    }

    public void setTouchPointFieldResearcherDTOList(List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList) {
        this.touchPointFieldResearcherDTOList = touchPointFieldResearcherDTOList;
    }
}
