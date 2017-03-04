package common.api;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.view.AbstractView;
import touchpoint.aux.TouchPointFieldResearcherListAdapter;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class APIGetTouchPointListOfRegisteredJourney extends APIFacade<TouchPointFieldResearcherListDTO, SdtUserDTO> implements APIExecutor<TouchPointFieldResearcherListDTO> {
    public APIGetTouchPointListOfRegisteredJourney(SdtUserDTO input, AbstractView view) {
        super(APIUrl.API_GET_TOUCH_POINT_LIST_OF_REGISTERED_JOURNEY, TouchPointFieldResearcherListDTO.class, input, APIUrl.METHOD_POST, view);
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(TouchPointFieldResearcherListDTO data) {
        ListView listView = (ListView) view.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_LIST_VIEW_TOUCH_POINT_LIST);
        listView.setAdapter(new TouchPointFieldResearcherListAdapter(view.getContext(), data.getTouchPointFieldResearcherDTOList()));
        TextView textView = (TextView) view.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_TEXT_VIEW_JOURNEY_NAME);
        textView.setText(data.getTouchPointFieldResearcherDTOList().get(0).getTouchpointDTO().getJourneyDTO().getJourneyName());
        Button button = (Button) view.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_BUTTON_SUBMIT_JOURNEY);
        button.setVisibility(View.VISIBLE);
        for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO : data.getTouchPointFieldResearcherDTOList()) {
            if (!ConstantValues.OTHERS_STATUS_DONE.equals(touchPointFieldResearcherDTO.getStatus())) {
                button.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }
}
