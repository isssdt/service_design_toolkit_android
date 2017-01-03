package touchpoint.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 12/31/16.
 */

public class TouchPointHolder extends RecyclerView.ViewHolder {
    private TextView channelTextView;
    private TextView nameTextView;
    private TextView stausTextView;

    public TouchPointHolder(View itemView) {
        super(itemView);
        channelTextView = (TextView) itemView.findViewById(R.id.channel);
        nameTextView = (TextView) itemView.findViewById(R.id.name);
        stausTextView = (TextView) itemView.findViewById(R.id.status);
    }

    public TextView getChannelTextView() {
        return channelTextView;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getStausTextView() {
        return stausTextView;
    }
}
