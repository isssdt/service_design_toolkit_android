package common.action;

import java.util.Observer;

import common.controller.AbstractController;

/**
 * Created by longnguyen on 1/4/17.
 */

public interface AbstractAction extends Observer {
    public boolean actionValidation(AbstractController abstractController);
}
