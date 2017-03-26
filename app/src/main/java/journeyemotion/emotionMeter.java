package journeyemotion;

/**
 * Created by Gunjan Pathak on 14/01/2017.
 */

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import common.action.ActionFactoryProducer;
import common.action.ActionOnBackClick;
import common.constants.APIUrl;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.io.File;
import java.util.ArrayList;

import connectionStatus.AppStatus;
import journey.activity.JourneyListActivity;
import poc.servicedesigntoolkit.getpost.MainActivity;
import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.Touchpoint.Touchpoint_model;
import journey.dto.JourneyDTO;
import touchpoint.dto.TouchPointDTO;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

public class emotionMeter extends AppCompatActivity {


    String Username, JourneyName;
    String TOUCHPOINTLIST_URL = APIUrl.API_GET_RESEARCH_WORK_LIST_BY_JOURNEY_NAME_AND_USERNAME;
    ArrayList<Touchpoint_model> touchpointData;
    GraphView graph;
    LineGraphSeries<DataPoint> series,seriesRef,seriesSample;
    TextView touchpointname, action, reaction;
    ImageView imageView;
    String TAG="emotion";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualization_emotion);

        touchpointData = new ArrayList<Touchpoint_model>();

            graph = (GraphView) findViewById(R.id.graph);
            touchpointname = (TextView) findViewById(R.id.touchpointname);
            action = (TextView) findViewById(R.id.action);
            reaction = (TextView) findViewById(R.id.reaction);
            imageView = (ImageView) findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();
        Username = (String) extras.get("Username");
        JourneyName = (String) extras.get("JourneyName");
        new HttpRequestTask().execute();
        //updateGraph(graph);

        getSupportActionBar().setTitle(JourneyName);
    }

    public void updateGraph(GraphView graph) {
        series = new LineGraphSeries<>(generateData(graph));
        graph.addSeries(series);
//        // enable scaling and scrolling
        //graph.getViewport().setScalable(true);
        //graph.getViewport().setScrollable(true);

        int count1 = touchpointData.size();
        Log.d("TAG","count updategraph"+count1);
       DataPoint[] valueRef=new DataPoint[count1+1];

        for (int ref=0;ref<count1+1;ref++){
            Log.d("TAG","inside if"+ref);
            valueRef[ref]=new DataPoint(ref,3);
            Log.d("TAG","valueRef"+valueRef[ref]);

        }

        seriesRef= new LineGraphSeries<>(valueRef);
        graph.addSeries(seriesRef);
        seriesRef.setTitle("Random Curve 1");
        seriesRef.setColor(Color.GREEN);

//        seriesSample= new LineGraphSeries<>(new DataPoint[]{new DataPoint(0,4),new DataPoint(1,4),new DataPoint(2,4),new DataPoint(3,4)});
//        graph.addSeries(seriesSample);

        Log.d("scale",""+graph.getViewport().getMaxYAxisSize());

//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//        staticLabelsFormatter.setVerticalLabels(new String[] {"0","1", "2", "3","4","5"});
        // set manual X bounds
//        graph.getViewport().setYAxisBoundsManual(true);
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(5);
//
//        graph.getViewport().setXAxisBoundsManual(true);
//        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(10);
//
//        // enable scaling and scrolling
//        graph.getViewport().setScalable(true);
//        graph.getViewport().setScalableY(true);

        graph.addSeries(series);

        // graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        series.setDrawDataPoints(true);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
            for (int i=0;i<touchpointData.size();i++)
            {
                int x=i;
                x++;
                if(x == dataPoint.getX()) {
                    touchpointData.get(i).getName();
                    Log.d("ontapdata",""+touchpointData.get(i).getName()+touchpointData.get(i).getRating()+"datapoint"+dataPoint.getX());
                    touchpointname.setText(touchpointData.get(i).getName());
                    action.setText(touchpointData.get(i).getAction());
                    reaction.setText(touchpointData.get(i).getReaction());
                    if (touchpointData.get(i).getPhotoLocation()!= null){
                        File imgFile = new File(touchpointData.get(i).getPhotoLocation());
                        if(imgFile.exists()){
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            imageView.setImageBitmap(myBitmap);
                        }
                    }
                    else
                    imageView.setImageBitmap(null);
                }
            }
            }
        });


    }
    private DataPoint[] generateData(GraphView graph) {
        Log.d("generate","generate");
        int count = touchpointData.size();
        Log.d("touchpointData size",""+count);
        double x=0,y=0;
        DataPoint[] values = new DataPoint[count+1];
        int i=1;
        String[] xAxis=new String[count+1],yAxis=new String[count];


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        values[0]=new DataPoint(0,0);
        xAxis[0]="0";
        for (Touchpoint_model touchpoint_model : touchpointData)

        {
            Log.d("generate name",""+touchpoint_model.getName());
            // x= touchpoint_model.getId();
            Log.d("generate id",""+x+i);
            y= Double.parseDouble(touchpoint_model.getRating());
            Log.d("generate rating",""+touchpoint_model.getRating());

            DataPoint v = new DataPoint(i, y);
            values[i] = v;
            Log.d("values",""+values[i]);

            xAxis[i]=touchpoint_model.getName();

            i++;


        }
        for(String s:xAxis)
        {
            Log.d("xxx",""+s);
        }
        staticLabelsFormatter.setHorizontalLabels(xAxis);
        staticLabelsFormatter.setVerticalLabels(new String[] {"0","1", "2", "3","4","5"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        return values;
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, TouchPointFieldResearcherListDTO> {
        @Override
        protected TouchPointFieldResearcherListDTO doInBackground(Void... params) {
            try {
                Log.d("Username",Username);
                Log.d("JourneyName",JourneyName);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(Username);

                FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
                fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);

                JourneyDTO journeyDTO = new JourneyDTO();
                journeyDTO.setJourneyName(JourneyName);

                TouchPointDTO touchPointDTO = new TouchPointDTO();
                touchPointDTO.setJourneyDTO(journeyDTO);

                TouchPointFieldResearcherDTO touchPointFieldResearcherDTO1 = new TouchPointFieldResearcherDTO();
                touchPointFieldResearcherDTO1.setFieldResearcherDTO(fieldResearcherDTO);
                touchPointFieldResearcherDTO1.setTouchpointDTO(touchPointDTO);

                TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO =
                        restTemplate.postForObject(TOUCHPOINTLIST_URL, touchPointFieldResearcherDTO1, TouchPointFieldResearcherListDTO.class);

                for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO :
                        touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList()) {

                    Touchpoint_model model=new Touchpoint_model();
                    model.setRating(touchPointFieldResearcherDTO.getRatingDTO().getValue());
                    model.setReaction(touchPointFieldResearcherDTO.getReaction());
                    model.setComment(touchPointFieldResearcherDTO.getComments());
                    model.setId(touchPointFieldResearcherDTO.getTouchpointDTO().getId());
                    model.setChannel_desc(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDescription());
                    model.setAction(touchPointFieldResearcherDTO.getTouchpointDTO().getAction());
                    model.setName(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc());
                    model.setPhotoLocation(touchPointFieldResearcherDTO.getPhotoLocation());
                    touchpointData.add(model);


                }
                for (Touchpoint_model t:touchpointData) {
                    Log.d("name", "" + t.getName());
                    Log.d("rating", "" + t.getRating());
                    Log.d("channel", "" + t.getChannel_desc());
                }

                return touchPointFieldResearcherListDTO;
            } catch (Exception e) {
                Log.e("Touchpoint", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO) {
            updateGraph(graph);
        }
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(),"Thank you for the participation for the journey",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(emotionMeter.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}