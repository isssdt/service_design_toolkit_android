package common.api;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.view.AbstractView;
import journey.aux_android.JourneyRecycleAdapter;
import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 3/3/17.
 */

public class APIGetJourneyListForRegister extends APIFacade<JourneyListDTO, SdtUserDTO> implements APIExecutor<JourneyListDTO> {
    public APIGetJourneyListForRegister(SdtUserDTO input, AbstractView view) {
        super(APIUrl.API_GET_JOURNEY_LIST_FOR_REGISTER, JourneyListDTO.class, input, APIUrl.METHOD_POST, view);
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(JourneyListDTO data) {

        for(JourneyDTO journeyDTO : data.getJourneyDTOList()){
            Log.d("JOURNEY NAME :",journeyDTO.getJourneyName());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        JourneyRecycleAdapter journeyRecycleAdapter = new JourneyRecycleAdapter(data.getJourneyDTOList(), view);
        RecyclerView journeyListRecyclerView = (RecyclerView) view.getComponent(ConstantValues.COMPONENT_JOURNEY_LIST_VIEW_RECYCLE_VIEW);
        journeyListRecyclerView.setHasFixedSize(true);
        journeyListRecyclerView.setLayoutManager(linearLayoutManager);
        journeyListRecyclerView.setAdapter(journeyRecycleAdapter);
        journeyRecycleAdapter.notifyDataSetChanged();
    }
}
