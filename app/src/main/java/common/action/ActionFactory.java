package common.action;

import android.view.View;

import common.view.AbstractView;
import main.action.ACTION_BACK_MAIN;
import main.action.ACTION_BUTTON_MAIN_RESEARCH_LIST;
import main.view.MainView;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ActionFactory {
    public static ActionHandler initActionHandler(View view) {
        if (R.id.researchList == view.getId()) {
            return new ACTION_BUTTON_MAIN_RESEARCH_LIST();
        }
        return null;
    }

    public static ActionHandler initActionHandlerForBack(AbstractView abstractView) {
        if (abstractView instanceof MainView) {
            return new ACTION_BACK_MAIN();
        }
        return null;
    }
}
