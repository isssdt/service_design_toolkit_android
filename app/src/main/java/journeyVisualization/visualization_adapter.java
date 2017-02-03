package journeyVisualization;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.Touchpoint.Touchpoint_model;
import timeline.TimelineView;

/**
 * Created by Gunjan Pathak on 11/01/2017.
 */

public class visualization_adapter extends ArrayAdapter<Touchpoint_model> {
private final LayoutInflater layoutInflater;
    Integer i;
public visualization_adapter(Context context, List<Touchpoint_model> objects) {
        super(context, 0, objects);
        i = objects.size()-1;

        layoutInflater = LayoutInflater.from(context);
        }

@Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
        convertView = layoutInflater.inflate(R.layout.visualization_journey_item, parent, false);
        viewHolder = new ViewHolderItem();
        viewHolder.text = (TextView) convertView.findViewById(R.id.textView);
        viewHolder.timeline = (TimelineView) convertView.findViewById(R.id.timeline);
        convertView.setTag(viewHolder);
        } else {
        viewHolder = (ViewHolderItem) convertView.getTag();
        }

    Touchpoint_model data = getItem(position);

        viewHolder.text.setText(data.getName());

        if(0 == position){
            viewHolder.timeline.setTimelineType(TimelineView.TYPE_START);
        }else if(i == position){
            viewHolder.timeline.setTimelineType(TimelineView.TYPE_END);
        }else
        viewHolder.timeline.setTimelineType(TimelineView.TYPE_MIDDLE);


        viewHolder.timeline.setTimelineAlignment(TimelineView.ALIGNMENT_DEFAULT);
    Log.d("Status",data.getStatus());
        if("DONE".equals(data.getStatus())){
            viewHolder.text.setTextColor(Color.GREEN);
        }
        return convertView;
        }

static class ViewHolderItem {
    TextView text;
    TimelineView timeline;
}
}

