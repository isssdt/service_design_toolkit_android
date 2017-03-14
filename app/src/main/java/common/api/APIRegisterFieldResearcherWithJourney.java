package common.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.view.AbstractView;
import journey.dto.JourneyFieldResearcherDTO;
import touchpoint.activity.TouchPointListActivity;

/**
 * Created by longnguyen on 3/3/17.
 */

public class APIRegisterFieldResearcherWithJourney extends APIFacade<JourneyFieldResearcherDTO, JourneyFieldResearcherDTO> implements APIExecutor<JourneyFieldResearcherDTO> {
    public APIRegisterFieldResearcherWithJourney(JourneyFieldResearcherDTO input, AbstractView view) {
        super(APIUrl.API_REGISTER_FIELD_RESEARCHER_WITH_JOURNEY, JourneyFieldResearcherDTO.class, input, APIUrl.METHOD_POST, view);
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(JourneyFieldResearcherDTO data) {

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("Trial", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TouchPointFieldResearcherListDTO",json);
        editor.commit();

        Intent i = new Intent(view.getContext(), TouchPointListActivity.class);
        i.putExtra(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO, data);
        i.putExtra("JourneyName", data.getJourneyDTO().getJourneyName());
        i.putExtra("Username", data.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        view.getContext().startActivity(i);
    }
}
