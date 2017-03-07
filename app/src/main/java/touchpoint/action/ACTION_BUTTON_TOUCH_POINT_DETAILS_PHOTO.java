package touchpoint.action;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import common.action.BaseAction;
import common.utils.Utils;
import common.view.AbstractView;
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
        if (ContextCompat.checkSelfPermission(abstractView.getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(abstractView.getContext(), new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            TouchPointFieldResearcherDTO touchPointFieldResearcherDTO =
                    (TouchPointFieldResearcherDTO) abstractView.getContext().getIntent().getExtras().get(TouchPointFieldResearcherDTO.class.toString());
            Utils.forwardToScreen(abstractView.getContext(), SelectPhoto.class, touchPointFieldResearcherDTO.getClass().toString(), touchPointFieldResearcherDTO);
        }
    }

    @Override
    public boolean validation(View view) {
        return false;
    }
}