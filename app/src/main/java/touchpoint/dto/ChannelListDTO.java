package touchpoint.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingyi on 11/12/16.
 */

public class ChannelListDTO {
    private List<ChannelDTO> channelDTOList = new ArrayList<>();

    public List<ChannelDTO> getChannelDTOList() {
        return channelDTOList;
    }

    public void setChannelDTOList(List<ChannelDTO> channelDTOList) {
        this.channelDTOList = channelDTOList;
    }
}
