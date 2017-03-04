package common.action;

import android.view.View;
import android.widget.AdapterView;

import common.view.AbstractView;
import main.action.ACTION_BACK_MAIN;
import main.view.MainView;
import touchpoint.action.ACTION_BACK_TOUCH_POINT_LIST;
import touchpoint.view.TouchPointListView;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ActionOnBackClickFactory extends ActionAbstractFactory {
    @Override
    public View.OnClickListener initOnClickAction(View view, AbstractView abstractView) {
        return null;
    }

    @Override
    public ActionOnBackClick initOnBackClickAction(AbstractView abstractView) {
        if (abstractView instanceof MainView) {
            return new ACTION_BACK_MAIN(abstractView);
        } else if (abstractView instanceof TouchPointListView) {
            return new ACTION_BACK_TOUCH_POINT_LIST(abstractView);
        }
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
