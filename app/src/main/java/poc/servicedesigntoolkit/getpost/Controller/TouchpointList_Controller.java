package poc.servicedesigntoolkit.getpost.Controller;
import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.List;

import poc.servicedesigntoolkit.getpost.Touchpoint.TouchpointList_Model;
import poc.servicedesigntoolkit.getpost.R;

public class TouchpointList_Controller extends ArrayAdapter<TouchpointList_Model>{

    private Activity activity;
    List<TouchpointList_Model> Items;
    int resources;

    Context context;

    public TouchpointList_Controller(Context context, int resource, List<TouchpointList_Model> items) {
        super(context, resource);
        Items = items;
        resources = resource;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TouchpointList_Model object = Items.get(position);


        View itemView = inflater.inflate(resources, parent, false);

        TextView name=(TextView)itemView.findViewById(R.id.name);

        TextView channel=(TextView)itemView.findViewById(R.id.channel);

        TextView status=(TextView)itemView.findViewById(R.id.status);

        name.setText(object.getName());

        channel.setText(object.getChannel());

        status.setText(object.getStatus());

        return itemView;

    }
}
