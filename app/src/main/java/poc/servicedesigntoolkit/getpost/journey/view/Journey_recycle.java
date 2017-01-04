package poc.servicedesigntoolkit.getpost.journey.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import journey.dto.JourneyListDTO;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.Touchpoint.RecyclerTouchListener;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan Pathak on 16-Dec-16.
 */


public class Journey_recycle extends Activity {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    Button signUp;
    SdtUserDTO sdtUserDTO;
    List<Journey_model> journeyDTOList;
    JourneyListDTO journeyListDTO;
    String Username;
    Journey_recycle mActivity;
    JourneyController journeyController;
    Journey_model journey_model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_recycle);

        journeyDTOList = new ArrayList<Journey_model>();
        journeyController = new JourneyController();

        Bundle extras = getIntent().getExtras();
        Username = (String) extras.get("Username");


        recyclerView = (RecyclerView) findViewById(R.id.journeyrecycle);
        signUp = (Button) findViewById(R.id.signup);


        mActivity = Journey_recycle.this;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                new JourneyAPI().execute();
            }
        });

       // new JourneyAPI().execute();
        Log.d("journeyDTOList",journeyDTOList.toString());
        LinearLayoutManager llm = new LinearLayoutManager(Journey_recycle.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewadapter = new Journey_recycle_adapter(journeyDTOList, Journey_recycle.this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(recyclerViewadapter);
        recyclerViewadapter.notifyDataSetChanged();

        Log.d("check flow","-->1");

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(view.getId() == signUp.getId()){

                }else{

                }
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    public void setJourneyList(List<Journey_model> journeyDTOs)
    {
        Log.d("journeyDTOs", journeyDTOs.toString());
        this.journeyDTOList = journeyDTOs;
   }
}
