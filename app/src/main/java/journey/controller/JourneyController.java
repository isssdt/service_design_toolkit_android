package journey.controller;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.Map;

import common.actionlistener.ViewController;
import common.actionlistener.ViewOfScreen;
import common.constants.ConstantValues;
import journey.action.ActionFieldResearcherRegisterJourney;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 12/29/16.
 */

public class JourneyController extends ViewController implements AdapterView.OnItemClickListener {
    public JourneyController(ViewOfScreen viewBind) {
        super(viewBind);
    }

    @Override
    public void addObservers() {
        addObserver(new ActionFieldResearcherRegisterJourney());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Map<String, Object> updateObjects = setUpdateObjects(adapterView);
        updateObjects.put(ConstantValues.ACTION_LISTENER_JOURNEY_SELECTED_POSITION_KEY, position);
        notifyObservers(updateObjects);
    }
}
