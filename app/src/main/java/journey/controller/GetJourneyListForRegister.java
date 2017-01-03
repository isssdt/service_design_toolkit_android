package journey.controller;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import common.api.APIContext;
import common.constants.APIUrl;
import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import journey.view.JourneyListView;

/**
 * Created by longnguyen on 12/31/16.
 */

public class GetJourneyListForRegister implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        APIContext apiContext = (APIContext) observable;
        if (!JourneyListView.class.toString().equals(apiContext.getControllerActionListener().getViewScreen().getClass().toString())) {
            return;
        }

        JourneyListDTO journeyListDTO = (JourneyListDTO)o;

        List<String> journeyNameList = new ArrayList<>();
        for (JourneyDTO journeyDTO : journeyListDTO.getJourneyDTOList()) {
            journeyNameList.add(journeyDTO.getJourneyName());
        }

        JourneyListView journeyListView = (JourneyListView)apiContext.getControllerActionListener().getViewScreen();
        ArrayAdapter journeyArrayAdapter = new ArrayAdapter(journeyListView.getContext(), android.R.layout.simple_list_item_1, journeyNameList);
        journeyListView.getListView().setAdapter(journeyArrayAdapter);
        journeyArrayAdapter.notifyDataSetChanged();
    }
}
