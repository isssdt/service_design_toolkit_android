package common.view;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import common.controller.CentralController;

/**
 * Created by longnguyen on 1/4/17.
 */

public abstract class AbstractView {
    private Activity context;
    protected Map<String, View> componentMap;
    private CentralController controller;

    public AbstractView(Activity context) {
        controller = new CentralController(this);
        this.context = context;
        componentMap = new HashMap<>();
        init();
        setUpListener();
    }

    public Activity getContext() {
        return context;
    }

    public View getComponent(String component) {
        return componentMap.get(component);
    }

    protected abstract void setUpListener();

    protected abstract void init();
}
