package touchpoint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by longnguyen on 11/6/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TouchPointDTO {
    private String touchPointDesc;

    public String getTouchPointDesc() {
        return touchPointDesc;
    }

    public void setTouchPointDesc(String touchPointDesc) {
        this.touchPointDesc = touchPointDesc;
    }
}
