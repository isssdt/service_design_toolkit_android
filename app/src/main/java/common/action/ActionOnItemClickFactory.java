package common.action;

import android.view.View;
import android.widget.AdapterView;

import common.view.AbstractView;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.action.ACTION_LIST_VIEW_TOUCH_POINT_LIST;

/**
 * Created by longnguyen on 3/4/17.
 */

public class ActionOnItemClickFactory extends ActionAbstractFactory {
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
        return null;
    }

    @Override
    public AdapterView.OnItemClickListener initOnItemClickAction(View view, AbstractView abstractView) {
        if (R.id.list == view.getId()) {
            return new ACTION_LIST_VIEW_TOUCH_POINT_LIST(abstractView);
        }
        return null;
    }
}
