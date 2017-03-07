package journey.aux_android;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 3/3/17.
 */

public class JourneyViewHolder extends RecyclerView.ViewHolder {
    private TextView journeyTextView;
    private Button journeyButton;

    public JourneyViewHolder(View itemView) {
        super(itemView);

        journeyTextView = (TextView) itemView.findViewById(R.id.journey_item);
        journeyButton = (Button) itemView.findViewById(R.id.signup);
    }

    public TextView getJourneyTextView() {
        return journeyTextView;
    }

    public void setJourneyTextView(TextView journeyTextView) {
        this.journeyTextView = journeyTextView;
    }

    public Button getJourneyButton() {
        return journeyButton;
    }

    public void setJourneyButton(Button journeyButton) {
        this.journeyButton = journeyButton;
    }
}
