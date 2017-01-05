package common.listener;

import java.util.Observer;

import common.controller.AbstractController;

/**
 * Created by longnguyen on 1/4/17.
 */

public interface ViewListener extends Observer {
    public boolean actionValidation(AbstractController abstractController);
}
