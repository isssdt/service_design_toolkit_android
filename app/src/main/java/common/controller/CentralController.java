package common.controller;

import android.view.View;

import common.action.ActionFactory;
import common.action.ActionHandler;
import common.view.AbstractView;

/**
 * Created by longnguyen on 3/3/17.
 */

public class CentralController {
    private AbstractView abstractView;

    public CentralController(AbstractView abstractView) {
        this.abstractView = abstractView;
    }

    public void actionHandler(View view) {
        ActionHandler actionHandler = ActionFactory.initActionHandler(view);
        if (actionHandler.validation(abstractView, view)) {
            actionHandler.execute(abstractView, view);
        }
    }
}
