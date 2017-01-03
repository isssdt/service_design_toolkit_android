package main.action;

import android.view.View;
import android.widget.Toast;

import java.util.Map;
import java.util.Observable;

import common.actionlistener.ViewController;
import common.actionlistener.ViewActionCommand;
import common.api.APIContext;
import common.constants.ConstantValues;
import main.view.MainView;
import poc.servicedesigntoolkit.getpost.R;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 1/1/17.
 */

public class ActionFieldResearcherSignIn implements ViewActionCommand {
    @Override
    public void update(Observable observable, Object o) {
        ViewController controllerActionListener = (ViewController)observable;
        Map<String, Object> updateObjects = (Map<String, Object>)o;

        if (((View)updateObjects.get(ConstantValues.ACTION_LISTENER_VIEW_KEY)).getId() != R.id.researchList) {
            return;
        }

        MainView mainView = (MainView) controllerActionListener.getViewScreen();
        if (!actionValidation(controllerActionListener)) {
            Toast.makeText(mainView.getContext().getApplicationContext(), ConstantValues.ALERT_MESSAGE_NO_USERNAME_ENTERED, Toast.LENGTH_SHORT).show();
            return;
        }

        SdtUserDTO sdtUserDTO = new SdtUserDTO();
        sdtUserDTO.setUsername(mainView.getUsername().getText().toString());
        new APIContext(controllerActionListener).registerFieldResearcher(sdtUserDTO);
    }

    @Override
    public boolean actionValidation(ViewController controllerSomePattern) {
        MainView mainView = (MainView) controllerSomePattern.getViewScreen();
        return null != mainView.getUsername().getText().toString() && !mainView.getUsername().getText().toString().isEmpty();
    }
}
