package common.actionlistener;

import android.content.Intent;
import android.view.View;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import common.constants.ConstantValues;

/**
 * Created by longnguyen on 12/31/16.
 */

public abstract class ViewController extends Observable {
    private ViewOfScreen viewScreen;

    public ViewController(ViewOfScreen viewBind) {
        this.viewScreen = viewBind;
        bindControllerOnView();
        addObservers();
    }

    public ViewOfScreen getViewScreen() {
        return viewScreen;
    }

    protected void bindControllerOnView() {
        viewScreen.bind(this);
    }

    protected abstract void addObservers();

    protected Map<String, Object> setUpdateObjects(View view) {
        setChanged();

        Map<String, Object> updateObjects = new HashMap<>();
        updateObjects.put(ConstantValues.ACTION_LISTENER_VIEW_KEY, view);

        return updateObjects;
    }

    public void forwardToScreen(Class nextScreen, String key, Serializable object) {
        Intent intent = new Intent(viewScreen.getContext(), nextScreen);
        intent.putExtra(key, object);
        viewScreen.getContext().startActivity(intent);
    }
}
