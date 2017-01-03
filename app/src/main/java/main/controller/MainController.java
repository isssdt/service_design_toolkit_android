package main.controller;

import android.view.View;

import common.actionlistener.ViewController;
import common.actionlistener.ViewOfScreen;
import main.action.ActionFieldResearcherSignIn;

/**
 * Created by longnguyen on 12/30/16.
 */

public class MainController extends ViewController implements View.OnClickListener {
    public MainController(ViewOfScreen viewBind) {
        super(viewBind);
    }

    @Override
    public void addObservers() {
        addObserver(new ActionFieldResearcherSignIn());
    }

    @Override
    public void onClick(View view) {
        notifyObservers(setUpdateObjects(view));
    }
}
