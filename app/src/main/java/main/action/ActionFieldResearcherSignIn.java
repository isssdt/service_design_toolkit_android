package main.action;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.Observable;

import common.api.APIContext;
import common.constants.ConstantValues;
import common.controller.AbstractController;
import common.listener.ViewListener;
import main.view.MainView;
import poc.servicedesigntoolkit.getpost.R;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 1/4/17.
 */

public class ActionFieldResearcherSignIn implements ViewListener {
    @Override
    public boolean actionValidation(AbstractController abstractController) {
        MainView mainView = (MainView) abstractController.getAbstractView();
        return null != ((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString() &&
                !((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString().isEmpty();
    }

    @Override
    public void update(Observable observable, Object o) {
        AbstractController abstractController = (AbstractController) observable;
        Map<String, Object> updateObjects = (Map<String, Object>) o;

        if (((View) updateObjects.get(ConstantValues.ACTION_LISTENER_VIEW_KEY)).getId() != R.id.researchList) {
            return;
        }

        MainView mainView = (MainView) abstractController.getAbstractView();
        if (!actionValidation(abstractController)) {
            Toast.makeText(mainView.getContext().getApplicationContext(), ConstantValues.ALERT_MESSAGE_NO_USERNAME_ENTERED, Toast.LENGTH_SHORT).show();
            return;
        }

        SdtUserDTO sdtUserDTO = new SdtUserDTO();
        sdtUserDTO.setUsername(((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString());
        new APIContext(abstractController).registerFieldResearcher(sdtUserDTO);
    }
}
