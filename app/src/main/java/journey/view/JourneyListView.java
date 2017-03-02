package journey.view;

import android.app.Activity;

import common.constants.ConstantValues;
import common.view.AbstractView;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 3/3/17.
 */

public class JourneyListView extends AbstractView {
    public JourneyListView(Activity context) {
        super(context);
    }

    @Override
    protected void setUpListener() {

    }

    @Override
    protected void init() {
        componentMap.put(ConstantValues.COMPONENT_JOURNEY_LIST_VIEW_BUTTON_SIGN_UP, getContext().findViewById(R.id.signup));
        componentMap.put(ConstantValues.COMPONENT_JOURNEY_LIST_VIEW_RECYCLE_VIEW, getContext().findViewById(R.id.journeyrecycle));
    }
}
