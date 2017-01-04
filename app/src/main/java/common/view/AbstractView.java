package common.view;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;

/**
 * Created by longnguyen on 1/4/17.
 */

public abstract class AbstractView {
    private Activity context;
    protected Map<String, View> componentMap;

    public AbstractView(Activity context) {
        this.context = context;
        componentMap = new HashMap<>();
        init();
    }

    public Activity getContext() {
        return context;
    }

    public View getComponent(String component) {
        return componentMap.get(component);
    }

    public abstract void bind(AbstractController abstractController);

    protected abstract void init();
}
