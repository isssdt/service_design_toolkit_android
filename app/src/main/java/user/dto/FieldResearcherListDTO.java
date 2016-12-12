package user.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingyi on 11/12/16.
 */

public class FieldResearcherListDTO {
    private List<FieldResearcherDTO> fieldResearcherDTOList = new ArrayList<>();

    public List<FieldResearcherDTO> getFieldResearcherDTOList() {
        return fieldResearcherDTOList;
    }

    public void setFieldResearcherDTOList(List<FieldResearcherDTO> fieldResearcherDTOList) {
        this.fieldResearcherDTOList = fieldResearcherDTOList;
    }
}
