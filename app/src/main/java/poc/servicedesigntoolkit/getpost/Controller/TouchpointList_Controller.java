package poc.servicedesigntoolkit.getpost.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import poc.servicedesigntoolkit.getpost.R;
import touchpoint.dto.TouchPointFieldResearcherDTO;

public class TouchpointList_Controller extends ArrayAdapter<TouchPointFieldResearcherDTO> {

    List<TouchPointFieldResearcherDTO> Items;
    int resources;
    Context context;
    private Activity activity;

    public TouchpointList_Controller(Context context, int resource, List<TouchPointFieldResearcherDTO> items) {
        super(context, resource);
        Items = items;
        resources = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TouchPointFieldResearcherDTO object = Items.get(position);


        View itemView = inflater.inflate(resources, parent, false);

        TextView name = (TextView) itemView.findViewById(R.id.name);

        TextView channel = (TextView) itemView.findViewById(R.id.channel);

        TextView status = (TextView) itemView.findViewById(R.id.status);

        name.setText(object.getTouchpointDTO().getChannelDescription());

        channel.setText(object.getTouchpointDTO().getChannelDTO().getChannelName());

        status.setText(object.getStatus());

        return itemView;

    }
}
