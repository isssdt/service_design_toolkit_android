package common.action;

import android.view.View;
import android.widget.AdapterView;

import common.view.AbstractView;
import main.action.ACTION_BUTTON_MAIN_RESEARCH_LIST;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.action.ACTION_BUTTON_TOUCH_POINT_DETAILS_PHOTO;
import touchpoint.action.ACTION_BUTTON_TOUCH_POINT_DETAILS_SUBMIT;
import touchpoint.action.ACTION_BUTTON_TOUCH_POINT_LIST_SUBMIT_JOURNEY;
import touchpoint.action.ACTION_FLOAT_BUTTON_TOUCH_POINT_LIST_NEW_TOUCPOINT;

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
        } else if (R.id.submit == view.getId()) {
            return new ACTION_BUTTON_TOUCH_POINT_DETAILS_SUBMIT(abstractView);
        } else if (R.id.photo == view.getId()) {
            return new ACTION_BUTTON_TOUCH_POINT_DETAILS_PHOTO(abstractView);
        } else if (R.id.fab == view.getId()){
            return new ACTION_FLOAT_BUTTON_TOUCH_POINT_LIST_NEW_TOUCPOINT(abstractView);
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
