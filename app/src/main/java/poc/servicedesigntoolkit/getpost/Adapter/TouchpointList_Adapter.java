/*
package poc.servicedesigntoolkit.getpost.Adapter;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

*/
/**
 * Created by Gunjan Pathak on 21-Oct-16.
 *//*


public class TouchpointList_Adapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] description;
    private final String[] radius;
    private final Integer[] imageId;
    public TouchpointList_Adapter(Activity context, String[] description, String[] radius, Integer[] imageId) {
        super(context, R.layout.list_single, description,radius);
        this.context = context;
        this.description = description;
        this.radius = radius;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        TextView radius = (TextView) rowView.findViewById(R.id.radius);

        description.setText(description[position]);
        radius.setText(radius[position]);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}*/
