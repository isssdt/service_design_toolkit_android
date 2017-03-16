package touchpoint.action;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import common.action.BaseAction;
import common.view.AbstractView;
import connectionStatus.AppStatus;
import photo.SelectPhoto;
import touchpoint.dto.TouchPointFieldResearcherDTO;

/**
 * Created by longnguyen on 3/5/17.
 */

public class ACTION_BUTTON_TOUCH_POINT_DETAILS_PHOTO extends BaseAction implements View.OnClickListener {
    public ACTION_BUTTON_TOUCH_POINT_DETAILS_PHOTO(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public void onClick(View view) {
        if (AppStatus.getInstance(view.getContext()).isOnline()) {

            if (ContextCompat.checkSelfPermission(abstractView.getContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(abstractView.getContext(), new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                TouchPointFieldResearcherDTO touchPointFieldResearcherDTO =
                        (TouchPointFieldResearcherDTO) abstractView.getContext().getIntent().getExtras().get(TouchPointFieldResearcherDTO.class.toString());
                Intent intent = new Intent(abstractView.getContext(), SelectPhoto.class);
                intent.putExtra(TouchPointFieldResearcherDTO.class.toString(), touchPointFieldResearcherDTO);
                intent.putExtra(Activity.class.toString(), abstractView.getContext().getClass().toString());
                abstractView.getContext().startActivity(intent);
            }
        }
        else {

                Snackbar.make(view,"You are not online!!!!",Snackbar.LENGTH_LONG).show();
                Log.v("Home", "############################You are not online!!!!");
            }



        }

    @Override
    public boolean validation(View view) {
        return false;
    }
}
