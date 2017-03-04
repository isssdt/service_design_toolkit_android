package touchpoint.action;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import common.action.ActionOnBackClick;
import common.action.BaseAction;
import common.view.AbstractView;
import poc.servicedesigntoolkit.getpost.MainActivity;

/**
 * Created by longnguyen on 3/4/17.
 */

public class ACTION_BACK_TOUCH_POINT_LIST extends BaseAction implements ActionOnBackClick {
    public ACTION_BACK_TOUCH_POINT_LIST(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public void onBackClick() {
        AlertDialog.Builder exit = new AlertDialog.Builder(abstractView.getContext());
        exit.setMessage(" Are you sure you want to exit Journey ?");
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(abstractView.getContext(), MainActivity.class);
                abstractView.getContext().startActivity(intent);
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
