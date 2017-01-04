package common.view;

import android.app.Activity;

import common.controller.AbstractController;

/**
 * Created by longnguyen on 1/4/17.
 */

public abstract class AbstractView {
    private Activity context;

    public AbstractView(Activity context) {
        this.context = context;
    }

    public Activity getContext() {
        return context;
    }

    public abstract void bind(AbstractController abstractController);
}
