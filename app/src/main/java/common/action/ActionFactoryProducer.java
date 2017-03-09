package common.action;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ActionFactoryProducer {
    public static ActionAbstractFactory getFactory(String actionType) {
        if (View.OnClickListener.class.toString().equals(actionType)) {
            return new ActionOnClickFactory();
        } else if (ActionOnBackClick.class.toString().equals(actionType)) {
            return new ActionOnBackClickFactory();
        } else if (RecycleViewOnItemClick.class.toString().equals(actionType)) {
            return new ActionRecycleViewOnItemClickFactory();
        } else if (AdapterView.OnItemClickListener.class.toString().equals(actionType)) {
            return new ActionOnItemClickFactory();
        }

        return null;
    }
}
