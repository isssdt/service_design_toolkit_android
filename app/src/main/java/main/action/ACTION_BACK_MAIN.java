package main.action;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import common.action.ActionOnBackClick;
import common.action.BaseAction;
import common.constants.ConstantValues;
import common.view.AbstractView;
import main.view.MainView;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ACTION_BACK_MAIN extends BaseAction implements ActionOnBackClick {
    public ACTION_BACK_MAIN(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public void onBackClick() {
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
    public boolean validation(View view) {
        return false;
    }
}
