package common.action;

import android.view.View;

import common.view.AbstractView;
import journey.ACTION_BUTTON_RECYCLE_JOURNEY_LIST_VIEW_SIGN_UP;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ActionRecycleViewOnItemClickFactory extends ActionAbstractFactory {
    @Override
    public View.OnClickListener initOnClickAction(View view, AbstractView abstractView) {
        return null;
    }

    @Override
    public ActionOnBackClick initOnBackClickAction(AbstractView abstractView) {
        return null;
    }

    @Override
    public RecycleViewOnItemClick initRecycleViewOnItemClick(View view, AbstractView abstractView, int position) {
        if (R.id.signup == view.getId()) {
            return new ACTION_BUTTON_RECYCLE_JOURNEY_LIST_VIEW_SIGN_UP(abstractView, position);
        }
        return null;
    }
}
