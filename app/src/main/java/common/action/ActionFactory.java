package common.action;

import android.view.View;

import main.action.ACTION_BUTTON_MAIN_RESEARCH_LIST;
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
}
