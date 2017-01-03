package journey.view;

import android.app.Activity;
import android.widget.ListView;

import common.actionlistener.ViewController;
import common.actionlistener.ViewOfScreen;
import journey.controller.JourneyController;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by longnguyen on 12/31/16.
 */

public class JourneyListView extends ViewOfScreen {
    private ListView listView;

    public JourneyListView(Activity context) {
        super(context);
        listView = (ListView) context.findViewById(R.id.listView);
    }

    public ListView getListView() {
        return listView;
    }

    @Override
    public void bind(ViewController viewActionListener) {
        listView.setOnItemClickListener((JourneyController)viewActionListener);
    }
}
