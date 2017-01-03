package poc.servicedesigntoolkit.getpost.journey.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by Gunjan Pathak on 17-Dec-16.
 */

public class Journey_recycle_adapter extends RecyclerView.Adapter<Journey_recycle_adapter.ViewHolder> {

    Context context;

    List<Journey_model> getDataAdapter;
    JourneyController journeyController;

    public Journey_recycle_adapter(List<Journey_model> getDataAdapter, Context context) {
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
        Log.d("check flow","-->2");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.journey_recycle_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Journey_model getDataAdapter1 = getDataAdapter.get(position);
        Log.d("check flow","-->1");
        holder.JourneyTextView.setText(getDataAdapter1.getJourneyName());
        Log.d("check flow","->"+getDataAdapter1.getJourneyName());
    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView JourneyTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            JourneyTextView = (TextView) itemView.findViewById(R.id.journey_item);
        }
    }
}
