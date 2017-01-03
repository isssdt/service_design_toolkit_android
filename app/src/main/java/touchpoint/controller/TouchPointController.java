package touchpoint.controller;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.Map;

import common.actionlistener.ViewController;
import common.actionlistener.ViewOfScreen;
import common.constants.ConstantValues;
import journey.action.ActionTouchPointSelected;

/**
 * Created by longnguyen on 12/29/16.
 */

public class TouchPointController extends ViewController implements RecyclerView.OnItemTouchListener, View.OnClickListener {
    public TouchPointController(ViewOfScreen viewBind) {
        super(viewBind);
    }


    @Override
    public void addObservers() {
        addObserver(new ActionTouchPointSelected());
        addObserver(new ActionSubmitResearchWork());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Map<String, Object> updateObjects = setUpdateObjects(rv);
        updateObjects.put(ConstantValues.ACTION_LISTENER_MOTION_EVENT_KEY, e);
        notifyObservers(updateObjects);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onClick(View view) {
        notifyObservers(setUpdateObjects(view));
    }
}
