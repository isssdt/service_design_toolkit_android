package common.actionlistener;

import java.util.Observer;

/**
 * Created by longnguyen on 1/1/17.
 */

public interface ViewActionCommand extends Observer {
    public boolean actionValidation(ViewController controllerActionListener);
}
