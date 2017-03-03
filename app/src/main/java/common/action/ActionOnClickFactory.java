package common.action;

import android.view.View;

import common.view.AbstractView;
import main.action.ACTION_BUTTON_MAIN_RESEARCH_LIST;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ActionOnClickFactory extends ActionAbstractFactory {
    @Override
    public View.OnClickListener initOnClickAction(View view, AbstractView abstractView) {
        if (R.id.researchList == view.getId()) {
            return new ACTION_BUTTON_MAIN_RESEARCH_LIST(abstractView);
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
}
