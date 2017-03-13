package touchpoint.action;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import addTouchpoint.AddNewTouchpoint;
import common.action.BaseAction;
import common.utils.Utils;
import common.view.AbstractView;
import connectionStatus.AppStatus;
import journey.dto.JourneyFieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan on 08-Mar-17.
 */

public class ACTION_FLOAT_BUTTON_TOUCH_POINT_LIST_NEW_TOUCPOINT  extends BaseAction implements View.OnClickListener {
    public ACTION_FLOAT_BUTTON_TOUCH_POINT_LIST_NEW_TOUCPOINT(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public void onClick(View view) {
        if (AppStatus.getInstance(view.getContext()).isOnline()) {

            SdtUserDTO sdtUserDTO = new SdtUserDTO();
        Utils.forwardToScreen(abstractView.getContext(), AddNewTouchpoint.class,"username",sdtUserDTO);
    }else {

        Snackbar.make(view,"You are not online!!!!",Snackbar.LENGTH_LONG).show();
        Log.v("Home", "############################You are not online!!!!");
    }
    }

    @Override
    public boolean validation(View view) {
        return false;
    }
}