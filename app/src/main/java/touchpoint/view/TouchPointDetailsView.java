package touchpoint.view;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import common.actionlistener.ViewController;
import common.actionlistener.ViewOfScreen;
import common.constants.ConstantValues;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.controller.TouchPointController;
import touchpoint.dto.TouchPointFieldResearcherDTO;

/**
 * Created by longnguyen on 1/1/17.
 */

public class TouchPointDetailsView extends ViewOfScreen {
    private EditText ET_touchPointName, ET_channelName, ET_channelDescription, ET_reaction, ET_comment, ET_action;
    private TextView TV_image;
    private RatingBar RB_rating;
    private Button Btn_submit, Btn_reset, Btn_photo;

    public TouchPointDetailsView(Activity context) {
        super(context);

        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO =
                (TouchPointFieldResearcherDTO)getContext().getIntent().getExtras().get(ConstantValues.BUNDLE_KEY_TOUCH_POINT_FIELD_RESEARCHER_DTO);

        getContext().setTitle(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());

        ET_touchPointName = (EditText)getContext().findViewById(R.id.touchpoint_name);
        ET_touchPointName.setText(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());

        ET_channelName = (EditText)getContext().findViewById(R.id.channel);
        ET_channelName.setText(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO().getChannelName());

        ET_channelDescription = (EditText)getContext().findViewById(R.id.Name);
        ET_channelDescription.setText(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDescription());

        ET_action = (EditText)getContext().findViewById(R.id.action);
        ET_action.setText(touchPointFieldResearcherDTO.getTouchpointDTO().getAction());

        ET_reaction = (EditText)getContext().findViewById(R.id.reaction);
        ET_comment = (EditText)getContext().findViewById(R.id.comment);

        TV_image = (TextView)getContext().findViewById(R.id.image);
        RB_rating = (RatingBar)getContext().findViewById(R.id.ratingBar);

        Btn_submit = (Button)getContext().findViewById(R.id.submit);
        Btn_reset = (Button)getContext().findViewById(R.id.reset);
        Btn_photo = (Button)getContext().findViewById(R.id.photo);
    }

    @Override
    public void bind(ViewController bindControllerOnView) {
        Btn_submit.setOnClickListener((TouchPointController)bindControllerOnView);
    }

    public EditText getET_reaction() {
        return ET_reaction;
    }

    public EditText getET_comment() {
        return ET_comment;
    }

    public RatingBar getRB_rating() {
        return RB_rating;
    }

    public Button getBtn_submit() {
        return Btn_submit;
    }
}
