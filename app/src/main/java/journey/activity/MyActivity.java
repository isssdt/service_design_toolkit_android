package journey.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.journey.view.Journey_recycle_adapter;
import poc.servicedesigntoolkit.getpost.journey.view.MyAdapter;

/**
 * Created by dingyi on 9/2/17.
 */

public class MyActivity extends JourneyListActivity {

    private List<Map<String, Object>> mData;

//    public void registeruser() {};
//    public void registeruser(String journey) {};

    public void showJouneyDetail() {};
    public void showJourneyDetail(int position) {};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_recycle);
        mData = getData();
        ListView listView = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        return list;
    }



}

//private class TouchListenerImp implements View.OnTouchListener {
//
//    public boolean onTouch(View v, MotionEvent event) {
//        MyActivity.this.info.setText("x="+event.getX()+"y="+event.getY());
//        return false;
//    }
//}
