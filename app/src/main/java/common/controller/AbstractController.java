package common.controller;

import android.content.Intent;
import android.view.View;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import common.constants.ConstantValues;
import common.view.AbstractView;

/**
 * Created by longnguyen on 1/4/17.
 */

public abstract class AbstractController extends Observable {
    private AbstractView abstractView;

    public AbstractController(AbstractView abstractView) {
        this.abstractView = abstractView;
        addObservers();
    }

    public AbstractView getAbstractView() {
        return abstractView;
    }

    protected abstract void addObservers();

    protected Map<String, Object> setUpdateObjects(View view) {
        setChanged();

        Map<String, Object> updateObjects = new HashMap<>();
        updateObjects.put(ConstantValues.ACTION_LISTENER_VIEW_KEY, view);

        return updateObjects;
    }

    public void forwardToScreen(Class nextScreen, String key, Serializable object) {
        Intent intent = new Intent(abstractView.getContext(), nextScreen);
        intent.putExtra(key, object);
        abstractView.getContext().startActivity(intent);
    }
}
