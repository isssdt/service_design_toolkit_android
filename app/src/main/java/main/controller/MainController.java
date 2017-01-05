package main.controller;

import android.view.View;

import common.controller.AbstractController;
import common.view.AbstractView;
import main.action.ActionFieldResearcherSignIn;

/**
 * Created by longnguyen on 1/4/17.
 */

public class MainController extends AbstractController implements View.OnClickListener {
    public MainController(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    protected void addObservers() {
        addObserver(new ActionFieldResearcherSignIn());
    }

    @Override
    public void onClick(View view) {
        notifyObservers(setUpdateObjects(view));
    }
}
