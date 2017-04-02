package touchpoint.aux_android;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import common.constants.ConstantValues;
import poc.servicedesigntoolkit.getpost.R;
import timeline.TimelineView;
import touchpoint.dto.TouchPointFieldResearcherDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class TouchPointFieldResearcherListAdapter extends ArrayAdapter<TouchPointFieldResearcherDTO> {
    public TouchPointFieldResearcherListAdapter(Context context, List<TouchPointFieldResearcherDTO> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TouchPointViewHolder touchPointViewHolder = new TouchPointViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.visualization_journey_item, parent, false);
            touchPointViewHolder.setText((TextView) convertView.findViewById(R.id.textView));
            touchPointViewHolder.setTimeline((TimelineView) convertView.findViewById(R.id.timeline));
            convertView.setTag(touchPointViewHolder);
        } else {
            touchPointViewHolder = (TouchPointViewHolder) convertView.getTag();
        }

        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = getItem(position);
        touchPointViewHolder.getText().setText(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());

        if (0 == position) {
            touchPointViewHolder.getTimeline().setTimelineType(TimelineView.TYPE_START);
            touchPointViewHolder.getText().setTextColor(Color.BLACK);
        } else if (getCount() - 1 == position) {
            touchPointViewHolder.getTimeline().setTimelineType(TimelineView.TYPE_END);
            touchPointViewHolder.getText().setTextColor(Color.BLACK);
        } else {
            touchPointViewHolder.getTimeline().setTimelineType(TimelineView.TYPE_MIDDLE);
            touchPointViewHolder.getText().setTextColor(Color.BLACK);
        }

        touchPointViewHolder.getTimeline().setTimelineAlignment(TimelineView.ALIGNMENT_DEFAULT);
        if (ConstantValues.OTHERS_STATUS_DONE.equals(touchPointFieldResearcherDTO.getStatus())) {
            touchPointViewHolder.getText().setTextColor(Color.GREEN);
        }
        return convertView;
    }
}
