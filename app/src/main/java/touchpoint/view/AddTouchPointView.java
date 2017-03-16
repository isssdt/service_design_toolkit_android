package touchpoint.view;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import common.constants.ConstantValues;
import common.view.AbstractView;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 3/16/17.
 */

public class AddTouchPointView extends AbstractView {
    public AddTouchPointView(Activity context) {
        super(context);
    }

    @Override
    protected void setUpListener() {

    }

    @Override
    protected void init() {
        List<String> timeUnitList = new ArrayList<>();
        timeUnitList.add(ConstantValues.OTHERS_TIME_UNIT_MINUTE);
        timeUnitList.add(ConstantValues.OTHERS_TIME_UNIT_HOUR);
        timeUnitList.add(ConstantValues.OTHERS_TIME_UNIT_DAY);
        Spinner timeSpinner = (Spinner) getContext().findViewById(R.id.time_unit);
        timeSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, timeUnitList));
        ((ArrayAdapter) timeSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> channelList = new ArrayList<>();
        channelList.add(ConstantValues.OTHERS_CHANNEL_FACE_2_FACE);
        channelList.add(ConstantValues.OTHERS_CHANNEL_KIOSK);
        channelList.add(ConstantValues.OTHERS_CHANNEL_WEBSITE);
        Spinner channelSpinner = (Spinner) getContext().findViewById(R.id.channel);
        timeSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, timeUnitList));
        ((ArrayAdapter) timeSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
