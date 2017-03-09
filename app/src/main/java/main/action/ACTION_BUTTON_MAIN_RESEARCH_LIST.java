package main.action;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import common.action.BaseAction;
import common.api.APIFieldResearcherRegister;
import common.constants.ConstantValues;
import common.view.AbstractView;
import main.view.MainView;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ACTION_BUTTON_MAIN_RESEARCH_LIST extends BaseAction implements View.OnClickListener {
    public ACTION_BUTTON_MAIN_RESEARCH_LIST(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public void onClick(View view) {
        Context context = abstractView.getContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "Trial", Context.MODE_PRIVATE);

        if (!validation(view)) {
            return;
        }
        MainView mainView = (MainView) abstractView;
        SdtUserDTO sdtUserDTO = new SdtUserDTO();
        String Username = ((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString();
        sdtUserDTO.setUsername(((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Username",Username);
        editor.commit();

        new APIFieldResearcherRegister(sdtUserDTO, abstractView).execute();
    }

    @Override
    public boolean validation(View view) {
        MainView mainView = (MainView) abstractView;
        if (null == ((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString() ||
                ((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString().isEmpty()) {
            Toast.makeText(mainView.getContext().getApplicationContext(), ConstantValues.ALERT_MESSAGE_NO_USERNAME_ENTERED, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
