package main.controller;

import android.view.View;

import common.controller.AbstractController;
import common.view.AbstractView;

/**
 * Created by longnguyen on 1/4/17.
 */

public class MainController extends AbstractController implements View.OnClickListener {
    public MainController(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    protected void addObservers() {
    }

    @Override
    public void onClick(View view) {
        notifyObservers(setUpdateObjects(view));
    }
}
