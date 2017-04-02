package touchpoint.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.action.ActionFactoryProducer;
import common.constants.ConstantValues;
import common.view.AbstractView;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.dto.TouchPointFieldResearcherDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class TouchPointDetailsView extends AbstractView {
    public TouchPointDetailsView(Activity context) {
        super(context);
    }

    @Override
    protected void setUpListener() {
        Button submit = (Button) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_BUTTON_SUBMIT);
        submit.setOnClickListener(ActionFactoryProducer.getFactory(View.OnClickListener.class.toString()).initOnClickAction(submit, this));
        ImageButton photo = (ImageButton) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_IMAGE_BUTTON);
        photo.setOnClickListener(ActionFactoryProducer.getFactory(View.OnClickListener.class.toString()).initOnClickAction(photo, this));
    }

    @Override
    protected void init() {
        Bundle extras = getContext().getIntent().getExtras();
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) extras.get(TouchPointFieldResearcherDTO.class.toString());

        ((EditText) getContext().findViewById(R.id.touchpoint_name)).setText(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());
        ((EditText) getContext().findViewById(R.id.channel)).setText(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO().getChannelName());
        ((EditText) getContext().findViewById(R.id.Name)).setText(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDescription());
        ((EditText) getContext().findViewById(R.id.action)).setText(touchPointFieldResearcherDTO.getTouchpointDTO().getAction());
        ((EditText) getContext().findViewById(R.id.expected)).setText(touchPointFieldResearcherDTO.getTouchpointDTO().getDuration() +
                " " + touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getDataValue());

        List<String> timeUnit = new ArrayList<>();
        timeUnit.add(ConstantValues.OTHERS_TIME_UNIT_MINUTE);
        timeUnit.add(ConstantValues.OTHERS_TIME_UNIT_HOUR);
        timeUnit.add(ConstantValues.OTHERS_TIME_UNIT_DAY);
        Spinner spinner = (Spinner) getContext().findViewById(R.id.time_unit);
        ArrayAdapter<String> data =new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, timeUnit);
        spinner.setAdapter(data);
        ((ArrayAdapter) spinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (!touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getDataValue().equals(null)) {
            int spinnerPosition = data.getPosition(touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getDataValue());
            spinner.setSelection(spinnerPosition);
        }

        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_REACTION, getContext().findViewById(R.id.reaction));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_COMMENT, getContext().findViewById(R.id.comment));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_ACTUAL_DURATION, getContext().findViewById(R.id.actual_time));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_TEXT_VIEW_IMAGE, getContext().findViewById(R.id.imagelabel));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_IMAGE_PATH, getContext().findViewById(R.id.imagePathEdit));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_RATING_BAR, getContext().findViewById(R.id.ratingBar));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_BUTTON_SUBMIT, getContext().findViewById(R.id.submit));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_IMAGE_BUTTON, getContext().findViewById(R.id.photo));
        componentMap.put(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_SPINNER_ACTUAL_DURATION_UNIT, spinner);

        if (null != touchPointFieldResearcherDTO.getRatingDTO() && null != touchPointFieldResearcherDTO.getRatingDTO().getValue()
                && !touchPointFieldResearcherDTO.getRatingDTO().getValue().isEmpty()) {
            RatingBar ratingBar = (RatingBar) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_RATING_BAR);
            ratingBar.setRating(Float.parseFloat(touchPointFieldResearcherDTO.getRatingDTO().getValue()));
            ratingBar.setIsIndicator(true);
            ((EditText) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_REACTION)).setText(touchPointFieldResearcherDTO.getReaction());
            ((EditText) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_COMMENT)).setText(touchPointFieldResearcherDTO.getComments());
            if (null != touchPointFieldResearcherDTO.getDuration()) {
                ((EditText) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_ACTUAL_DURATION)).setText(String.valueOf(touchPointFieldResearcherDTO.getDuration()));
            }
        }


        if (null != touchPointFieldResearcherDTO.getPhotoLocation() && !touchPointFieldResearcherDTO.getPhotoLocation().isEmpty()) {
            ((TextView) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_TEXT_VIEW_IMAGE)).setVisibility(View.VISIBLE);
            EditText imagePath = (EditText) getComponent(ConstantValues.COMPONENT_TOUCH_POINT_DETAILS_VIEW_EDIT_TEXT_IMAGE_PATH);
            imagePath.setVisibility(View.VISIBLE);
            imagePath.setEnabled(false);
            imagePath.setText(touchPointFieldResearcherDTO.getPhotoLocation());
        }
    }
}
