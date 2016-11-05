package poc.servicedesigntoolkit.getpost.Touchpoint;

/**
 * Created by Gunjan Pathak on 28-Oct-16.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import poc.servicedesigntoolkit.getpost.R;

public class TouchpointAdapter extends RecyclerView.Adapter<TouchpointAdapter.ViewHolder> {

    Context context;

    List<TouchpointList_Model> getDataAdapter;

    public TouchpointAdapter(List<TouchpointList_Model> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.touchpoint_item_recycle, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TouchpointList_Model getDataAdapter1 =  getDataAdapter.get(position);

        holder.NameTextView.setText(getDataAdapter1.getName());

        holder.IdTextView.setText(String.valueOf(getDataAdapter1.getChannel()));

        holder.PhoneNumberTextView.setText(getDataAdapter1.getStatus());


    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView IdTextView;
        public TextView NameTextView;
        public TextView PhoneNumberTextView;


        public ViewHolder(View itemView) {

            super(itemView);

            IdTextView = (TextView) itemView.findViewById(R.id.channel) ;
            NameTextView = (TextView) itemView.findViewById(R.id.name) ;
            PhoneNumberTextView = (TextView) itemView.findViewById(R.id.status) ;

        }
    }
}