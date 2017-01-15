package poc.servicedesigntoolkit.getpost;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.journey.view.Journey_model;

/**
 * Created by Gunjan Pathak on 02-Jan-17.
 */


public class Journey_Adapter extends RecyclerView.Adapter<Journey_Adapter.ViewHolder> {

    Context context;

    List<Journey_model> getDataAdapter;

    public Journey_Adapter(List<Journey_model> getDataAdapter, Context context) {
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }


    @Override
    public Journey_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.journey_recycle_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Journey_Adapter.ViewHolder holder, int position) {
        Journey_model getDataAdapter1 = getDataAdapter.get(position);
        holder.journeyname.setText(getDataAdapter1.getJourneyName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView journeyname;
        public Button signup;

        public ViewHolder(View itemView) {
            super(itemView);
            journeyname = (TextView) itemView.findViewById(R.id.journey_item);
            signup = (Button) itemView.findViewById(R.id.signup);
        }
    }



}