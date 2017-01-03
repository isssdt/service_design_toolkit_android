package common.api.handler;

import android.support.v7.widget.LinearLayoutManager;

import java.util.Observable;
import java.util.Observer;

import common.api.APIContext;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import touchpoint.extension.android.TouchPointListAdapter;
import touchpoint.view.TouchPointListView;

/**
 * Created by longnguyen on 12/31/16.
 */

public class GetTouchPointListOfRegisteredJourney implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        APIContext apiContext = (APIContext) observable;
        if (!TouchPointListView.class.toString().equals(apiContext.getControllerActionListener().getViewScreen().getClass().toString())) {
            return;
        }

        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = (TouchPointFieldResearcherListDTO)o;
        TouchPointListView touchPointListView = (TouchPointListView)apiContext.getControllerActionListener().getViewScreen();
        LinearLayoutManager llm = new LinearLayoutManager(touchPointListView.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        TouchPointListAdapter touchPointListAdapter = new TouchPointListAdapter(touchPointFieldResearcherListDTO, touchPointListView.getContext());
        touchPointListView.getRecyclerView().setLayoutManager(llm);
        touchPointListView.getRecyclerView().setAdapter(touchPointListAdapter);
        touchPointListAdapter.notifyDataSetChanged();
    }
}
