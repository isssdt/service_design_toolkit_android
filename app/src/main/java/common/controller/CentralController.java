package common.controller;

import common.view.AbstractView;

/**
 * Created by longnguyen on 3/3/17.
 */

public class CentralController {
    private AbstractView abstractView;

    public CentralController(AbstractView abstractView) {
        this.abstractView = abstractView;
    }

    public AbstractView getAbstractView() {
        return abstractView;
    }
}
