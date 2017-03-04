package touchpoint.action;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import common.action.BaseAction;
import common.view.AbstractView;
import poc.servicedesigntoolkit.getpost.TouchpointDetails;
import touchpoint.dto.TouchPointFieldResearcherDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class ACTION_LIST_VIEW_TOUCH_POINT_LIST extends BaseAction implements AdapterView.OnItemClickListener {
    public ACTION_LIST_VIEW_TOUCH_POINT_LIST(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public boolean validation(View view) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) adapterView.getItemAtPosition(position);

        Intent i = new Intent(abstractView.getContext(), TouchpointDetails.class);
        i.putExtra("Action", touchPointFieldResearcherDTO.getTouchpointDTO().getAction());
        i.putExtra("Channel", touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO().getChannelName());
        i.putExtra("Channel_Desc", touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDescription());
        i.putExtra("Touchpoint", touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());
        i.putExtra("Id", touchPointFieldResearcherDTO.getTouchpointDTO().getId());
        i.putExtra("Username", touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        i.putExtra("JourneyName", touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO().getJourneyName());
        i.putExtra("Expected_time", touchPointFieldResearcherDTO.getTouchpointDTO().getDuration());
        i.putExtra("Expected_unit", touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getDataValue());

        if (null != touchPointFieldResearcherDTO.getRatingDTO().getValue()) {
            i.putExtra("rating", touchPointFieldResearcherDTO.getRatingDTO().getValue());
            i.putExtra("comment", touchPointFieldResearcherDTO.getComments());
            i.putExtra("reaction", touchPointFieldResearcherDTO.getReaction());
            i.putExtra("Actual_time", touchPointFieldResearcherDTO.getDuration().toString());
            i.putExtra("Actual_unit", touchPointFieldResearcherDTO.getDurationUnitDTO().getDataValue());
        }
        abstractView.getContext().startActivity(i);
    }
}
