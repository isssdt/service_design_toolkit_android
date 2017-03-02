package main.action;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import common.action.ActionHandler;
import common.constants.ConstantValues;
import common.view.AbstractView;
import main.view.MainView;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ACTION_BACK_MAIN implements ActionHandler {
    @Override
    public void execute(AbstractView abstractView, View view) {
        final MainView mainView = (MainView) abstractView;
        AlertDialog.Builder exit = new AlertDialog.Builder(mainView.getContext());
        exit.setMessage(ConstantValues.ALERT_MESSAGE_QUIT_CONFIRMATION);
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mainView.getContext().startActivity(intent);
            }
        });
        exit.setNegativeButton("No", null);
        exit.show();
    }

    @Override
    public boolean validation(AbstractView abstractView, View view) {
        return false;
    }
}
