package touchpoint.aux_android;

import android.widget.TextView;

import timeline.TimelineView;

/**
 * Created by longnguyen on 3/4/17.
 */

public class TouchPointViewHolder {
    private TextView text;
    private TimelineView timeline;

    public TouchPointViewHolder() {
    }

    public TouchPointViewHolder(TextView text, TimelineView timeline) {
        this.text = text;
        this.timeline = timeline;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public TimelineView getTimeline() {
        return timeline;
    }

    public void setTimeline(TimelineView timeline) {
        this.timeline = timeline;
    }
}
