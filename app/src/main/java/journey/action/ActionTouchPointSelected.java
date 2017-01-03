package journey.action;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.Map;
import java.util.Observable;

import common.actionlistener.ViewActionCommand;
import common.actionlistener.ViewController;
import common.constants.ConstantValues;
import journey.dto.JourneyFieldResearcherDTO;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.activity.TouchPointDetailsActivity;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.extension.android.TouchPointListAdapter;
import touchpoint.view.TouchPointListView;

/**
 * Created by longnguyen on 1/1/17.
 */

public class ActionTouchPointSelected implements ViewActionCommand {
    @Override
    public boolean actionValidation(ViewController controllerActionListener) {
        return false;
    }

    @Override
    public void update(Observable observable, Object o) {
        Map<String, Object> updateObjects = (Map<String, Object>)o;
        View recyclerView = (View) updateObjects.get(ConstantValues.ACTION_LISTENER_VIEW_KEY);

        if (recyclerView.getId() != R.id.recyclerView1) {
            return;
        }

        MotionEvent motionEvent = (MotionEvent)updateObjects.get(ConstantValues.ACTION_LISTENER_MOTION_EVENT_KEY);



        ViewController controllerActionListener = (ViewController)observable;

        View child = ((RecyclerView)recyclerView).findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        int position = ((RecyclerView)recyclerView).getChildAdapterPosition(child);

        TouchPointListView touchPointListView = (TouchPointListView)controllerActionListener.getViewScreen();
        TouchPointListAdapter touchPointListAdapter = (TouchPointListAdapter)touchPointListView.getRecyclerView().getAdapter();
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = touchPointListAdapter.getTouchPointFieldResearcherListDTO().getTouchPointFieldResearcherDTOList().get(position);

        JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO)controllerActionListener.getViewScreen().
                getContext().getIntent().getExtras().get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO);
        touchPointFieldResearcherDTO.getTouchpointDTO().setJourneyDTO(journeyFieldResearcherDTO.getJourneyDTO());

        controllerActionListener.forwardToScreen(TouchPointDetailsActivity.class, ConstantValues.BUNDLE_KEY_TOUCH_POINT_FIELD_RESEARCHER_DTO, touchPointFieldResearcherDTO);
    }
}
