package common.action;

import android.view.View;

import common.view.AbstractView;

/**
 * Created by longnguyen on 3/3/17.
 */

public abstract class BaseAction {
    protected AbstractView abstractView;
    protected int position;

    public BaseAction(AbstractView abstractView, int position) {
        this.abstractView = abstractView;
        this.position = position;
    }

    public BaseAction(AbstractView abstractView) {
        this.abstractView = abstractView;
    }

    public abstract boolean validation(View view);
}
