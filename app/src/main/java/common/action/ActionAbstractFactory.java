package common.action;

import android.view.View;

import common.view.AbstractView;

/**
 * Created by longnguyen on 3/3/17.
 */

public abstract class ActionAbstractFactory {
    public abstract View.OnClickListener initOnClickAction(View view, AbstractView abstractView);

    public abstract ActionOnBackClick initOnBackClickAction(AbstractView abstractView);

    public abstract RecycleViewOnItemClick initRecycleViewOnItemClick(View view, AbstractView abstractView, int position);
}
