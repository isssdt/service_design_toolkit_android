package poc.servicedesigntoolkit.getpost.journey.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import journey.activity.JourneyListActivity;
import journeyemotion.emotionMeter;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by Gunjan Pathak on 17-Dec-16.
 */

public class Journey_recycle_adapter extends RecyclerView.Adapter<Journey_recycle_adapter.ViewHolder> {

    Context context;

    List<Journey_model> getDataAdapter;
    JourneyListActivity journeyListActivity;

    public Journey_recycle_adapter(List<Journey_model> getDataAdapter, Context context) {
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        journeyListActivity = new JourneyListActivity();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.journey_recycle_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final Journey_model getDataAdapter1 = getDataAdapter.get(position);
        holder.JourneyTextView.setText(getDataAdapter1.getJourneyName());
        if("DONE".equals(getDataAdapter1.getCompleted())) {
            holder.viewJourney.setText("View");
        }
        holder.viewJourney.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("position",""+holder.viewJourney.getText());
                if ("View".equals(holder.viewJourney.getText())){
                    ((JourneyListActivity) context).ButtonAction("View",getDataAdapter1.getJourneyName(),getDataAdapter1.getStartDate(),getDataAdapter1.getEndDate());
                }else if ("Sign Up".equals(holder.viewJourney.getText())){
                    ((JourneyListActivity) context).ButtonAction("Sign up",getDataAdapter1.getJourneyName(),getDataAdapter1.getStartDate(),getDataAdapter1.getEndDate());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView JourneyTextView;
        public Button viewJourney;

        public ViewHolder(View itemView) {
            super(itemView);
            JourneyTextView = (TextView) itemView.findViewById(R.id.journey_item);
            viewJourney = (Button) itemView.findViewById(R.id.signup);
        }
    }
}
