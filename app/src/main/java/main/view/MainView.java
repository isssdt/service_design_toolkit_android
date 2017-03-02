package main.view;

import android.app.Activity;
import android.widget.Button;

import common.constants.ConstantValues;
import common.view.AbstractView;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 1/4/17.
 */

public class MainView extends AbstractView {
    public MainView(Activity context) {
        super(context);
    }

    @Override
    protected void setUpListener() {
        ((Button) componentMap.get(ConstantValues.COMPONENT_MAIN_VIEW_BUTTON_RESEARCH_LIST)).setOnClickListener(this);
    }

    @Override
    protected void init() {
        componentMap.put(ConstantValues.COMPONENT_MAIN_VIEW_BUTTON_RESEARCH_LIST, getContext().findViewById(R.id.researchList));
        componentMap.put(ConstantValues.COMPONENT_MAIN_VIEW_TEXT_VIEW_TEXT, getContext().findViewById(R.id.textView));
        componentMap.put(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME, getContext().findViewById(R.id.username));
    }
}
