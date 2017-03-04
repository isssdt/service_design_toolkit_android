package touchpoint.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import common.action.ActionFactoryProducer;
import common.constants.ConstantValues;
import common.view.AbstractView;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 3/4/17.
 */

public class TouchPointListView extends AbstractView {
    public TouchPointListView(Activity context) {
        super(context);
    }

    @Override
    protected void setUpListener() {
        ListView listView = (ListView) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_LIST_VIEW_TOUCH_POINT_LIST);
        listView.setOnItemClickListener(ActionFactoryProducer.getFactory(AdapterView.OnItemClickListener.class.toString()).initOnItemClickAction(listView, this));
        Button button = (Button) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_BUTTON_SUBMIT_JOURNEY);
        button.setOnClickListener(ActionFactoryProducer.getFactory(View.OnClickListener.class.toString()).initOnClickAction(button, this));
    }

    @Override
    protected void init() {
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_LIST_VIEW_TOUCH_POINT_LIST, getContext().findViewById(R.id.list));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_TEXT_VIEW_JOURNEY_NAME, getContext().findViewById(R.id.displayJourneyName));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_BUTTON_SUBMIT_JOURNEY, getContext().findViewById(R.id.submitJourney1));
    }
}
