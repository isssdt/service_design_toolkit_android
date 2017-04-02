package touchpoint.action;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import common.action.BaseAction;
import common.constants.ConstantValues;
import common.view.AbstractView;
import connectionStatus.AppStatus;
import photo.SelectPhoto;
import touchpoint.dto.RatingDTO;
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
        EditText imagepath = (EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_IMAGE_PATH);
        final EditText reaction = (EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_REACTION);
        final EditText comment = (EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_COMMENT);
        final RatingBar rating = (RatingBar) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_RATING_BAR);
        final EditText time = (EditText) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_ACTUAL_DURATION);
        Spinner time_unit = (Spinner) abstractView.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_SPINNER_ACTUAL_DURATION_UNIT);

        if (AppStatus.getInstance(view.getContext()).isOnline()) {

            if(imagepath.getText().length() != 0) {
                if(rating .getRating() == 0)
                    Toast.makeText(abstractView.getContext(), "Please enter the rating ", Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder exit = new AlertDialog.Builder(abstractView.getContext());
                    exit.setMessage(" Do you want to replace the Current Image ?");
                    exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (ContextCompat.checkSelfPermission(abstractView.getContext(), Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(abstractView.getContext(), new String[]{Manifest.permission.CAMERA}, 1);
                            } else {
                                TouchPointFieldResearcherDTO touchPointFieldResearcherDTO =
                                        (TouchPointFieldResearcherDTO) abstractView.getContext().getIntent().getExtras().get(TouchPointFieldResearcherDTO.class.toString());

                                if (0.0 != rating.getRating()) {
                                    touchPointFieldResearcherDTO.setRatingDTO(new RatingDTO());
                                    touchPointFieldResearcherDTO.getRatingDTO().setValue(String.valueOf(rating.getRating()));
                                }
                                if (null != comment.getText().toString() && !comment.getText().toString().isEmpty())
                                    touchPointFieldResearcherDTO.setComments(comment.getText().toString());

                                if (null != reaction.getText().toString() && !reaction.getText().toString().isEmpty())
                                    touchPointFieldResearcherDTO.setReaction(reaction.getText().toString());
                                if (null != time.getText().toString() && !time.getText().toString().isEmpty()) {
                                    touchPointFieldResearcherDTO.setDuration(Integer.valueOf(time.getText().toString()));
                                }

                                Intent intent = new Intent(abstractView.getContext(), SelectPhoto.class);
                                intent.putExtra(TouchPointFieldResearcherDTO.class.toString(), touchPointFieldResearcherDTO);
                                intent.putExtra(Activity.class.toString(), abstractView.getContext().getClass().toString());
                                abstractView.getContext().startActivity(intent);
                            }
                        }
                    });
                    exit.setNegativeButton("No", null);
                    exit.show();
                }
            }else{
                if (ContextCompat.checkSelfPermission(abstractView.getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(abstractView.getContext(), new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    if (rating.getRating() == 0)
                        Toast.makeText(abstractView.getContext(), "Please enter the rating ", Toast.LENGTH_SHORT).show();
                    else {
                        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO =
                                (TouchPointFieldResearcherDTO) abstractView.getContext().getIntent().getExtras().get(TouchPointFieldResearcherDTO.class.toString());

                        if (0.0 != rating.getRating()) {
                            touchPointFieldResearcherDTO.setRatingDTO(new RatingDTO());
                            touchPointFieldResearcherDTO.getRatingDTO().setValue(String.valueOf(rating.getRating()));
                        }
                        if (null != comment.getText().toString() && !comment.getText().toString().isEmpty())
                            touchPointFieldResearcherDTO.setComments(comment.getText().toString());

                        if (null != reaction.getText().toString() && !reaction.getText().toString().isEmpty())
                            touchPointFieldResearcherDTO.setReaction(reaction.getText().toString());
                        if (null != time.getText().toString() && !time.getText().toString().isEmpty()) {
                            touchPointFieldResearcherDTO.setDuration(Integer.valueOf(time.getText().toString()));
                        }

                        Intent intent = new Intent(abstractView.getContext(), SelectPhoto.class);
                        intent.putExtra(TouchPointFieldResearcherDTO.class.toString(), touchPointFieldResearcherDTO);
                        intent.putExtra(Activity.class.toString(), abstractView.getContext().getClass().toString());
                        abstractView.getContext().startActivity(intent);
                    }
                }
            }
        }
        else {

                Snackbar.make(view,"Please check Your Internet Connection!!!!",Snackbar.LENGTH_LONG).show();
                Log.v("Home", "############################You are not online!!!!");
            }



        }

    @Override
    public boolean validation(View view) {
        return false;
    }
}
