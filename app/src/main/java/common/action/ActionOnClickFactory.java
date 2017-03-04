package common.action;

import android.view.View;
import android.widget.AdapterView;

import common.view.AbstractView;
import main.action.ACTION_BUTTON_MAIN_RESEARCH_LIST;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.action.ACTION_BUTTON_TOUCH_POINT_LIST_SUBMIT_JOURNEY;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ActionOnClickFactory extends ActionAbstractFactory {
    @Override
    public View.OnClickListener initOnClickAction(View view, AbstractView abstractView) {
        if (R.id.researchList == view.getId()) {
            return new ACTION_BUTTON_MAIN_RESEARCH_LIST(abstractView);
        } else if (R.id.submitJourney1 == view.getId()) {
            return new ACTION_BUTTON_TOUCH_POINT_LIST_SUBMIT_JOURNEY(abstractView);
        }
        return null;
    }

    @Override
    public ActionOnBackClick initOnBackClickAction(AbstractView abstractView) {
        return null;
    }

    @Override
    public RecycleViewOnItemClick initRecycleViewOnItemClick(View view, AbstractView abstractView, int position) {
        return null;
    }

    @Override
    public AdapterView.OnItemClickListener initOnItemClickAction(View view, AbstractView abstractView) {
        return null;
    }
}
