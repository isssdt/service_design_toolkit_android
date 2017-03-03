package common.action;

import android.view.View;

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
        }
        return null;
    }
}
