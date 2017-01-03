package touchpoint.view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import common.actionlistener.ViewController;
import common.actionlistener.ViewOfScreen;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.controller.TouchPointController;

/**
 * Created by longnguyen on 12/31/16.
 */

public class TouchPointListView extends ViewOfScreen {
    private RecyclerView recyclerView;

    public TouchPointListView(Activity context) {
        super(context);
        recyclerView = (RecyclerView) getContext().findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void bind(ViewController bindControllerOnView) {
        recyclerView.addOnItemTouchListener((TouchPointController)bindControllerOnView);
    }
}
