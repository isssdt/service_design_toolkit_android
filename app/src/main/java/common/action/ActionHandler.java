package common.action;

import android.view.View;

import common.view.AbstractView;

/**
 * Created by longnguyen on 3/2/17.
 */

public interface ActionHandler {
    public void execute(AbstractView abstractView, View view);
    public boolean validation(AbstractView abstractView, View view);
}
