package poc.servicedesigntoolkit.getpost.journey.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import journey.activity.MyActivity;
import journeyemotion.emotionMeter;
import poc.servicedesigntoolkit.getpost.R;

/**
 * Created by dingyi on 9/2/17.
 */

public class MyAdapter extends Journey_recycle_adapter {

    MyActivity context;
    private LayoutInflater mInflater;

    private List<Map<String, Object>> mData;

    public MyAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public MyAdapter(MyActivity context){
        this.context = context;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            holder=new ViewHolder();

            convertView = mInflater.inflate(R.layout.activity_journey_list, null);
            holder.title = (TextView)convertView.findViewById(R.id.journey_item);
            //holder.info = (TextView)convertView.findViewById(R.id.info);
            holder.viewBtn = (Button)convertView.findViewById(R.id.signup);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.title.setText((String)mData.get(position).get("title"));
        //holder.info.setText((String)mData.get(position).get("info"));
        holder.viewBtn.setTag(position);

        holder.viewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showInfo(position);
            }
        });

        //holder.viewBtn.setOnClickListener(MyListener(position));

        return convertView;
    }

    public final class ViewHolder {
        public TextView title;
        //public TextView info;
        public Button viewBtn;
    }

    public void showInfo(int position){
        context.showJouneyDetail();

//        ImageView img=new ImageView(ListViewActivity.this);
//        img.setImageResource(R.drawable.b);
//        new AlertDialog.Builder(this).setView(img)
//                .setTitle("详情"+position)
//                .setMessage("菜名："+title[position]+"   价格:"+info[position])
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .show();
//    }

}

//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        final Journey_model getDataAdapter1 = getDataAdapter.get(position);
//        holder.JourneyTextView.setText(getDataAdapter1.getJourneyName());
//        if("DONE".equals(getDataAdapter1.getCompleted())){
//            Log.d("DONE",getDataAdapter1.getCompleted());
//            holder.viewJourney.setText("View");
//        }
//        holder.viewJourney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if("View".equals(holder.viewJourney.getText())){
//                    Intent i =new Intent(v.getContext(), emotionMeter.class);
//                    v.getContext().startActivity(i);
//                }else{
//                    context.registeruser();
//
//                }
//            }
//        });
//    }




//            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//                final Journey_model model =touchpointData.get(position);
//
//                startDate = model.getStartDate();
//                endDate = model.getEndDate();
//                format = new SimpleDateFormat("dd MMM yyyy");
//                start = format.format(startDate);
//                end = format.format(endDate);
//                JourneyName = model.getJourneyName();
//
//                if("DONE".equals(model.getCompleted())){
//                    Intent i = new Intent(JourneyListActivity.this,emotionMeter.class);
//                    i.putExtra("JourneyName", JourneyName);
//                    i.putExtra("Username", Username);
//                    startActivity(i);
//                }else{
//                    final AlertDialog.Builder adb = new AlertDialog.Builder(JourneyListActivity.this);
//                    adb.setTitle("Register");
//                    adb.setMessage("Journey Name : " + JourneyName+"\n"+
//                            "Date : "+start+" - "+end);
//                    adb.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (ContextCompat.checkSelfPermission(JourneyListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                                    != PackageManager.PERMISSION_GRANTED) {
//                                ActivityCompat.requestPermissions(JourneyListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, share_location_request_code);
//                            } else {
//                                Location location = locationManager.getLastKnownLocation(provider);
//                                if (location != null) {
//                                    System.out.println("Provider " + provider + " has been selected.");
//                                    onLocationChanged(location);
//                                } else {
//                                    Toast.makeText(getApplicationContext(),"Location not available",Toast.LENGTH_SHORT );
//                                }
//                                registeruser(JourneyName);
//                            }
//                        }
//                    });
//                    adb.setNegativeButton("Cancel", null);
//                    adb.show();
//
//                }
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//    }
//
}
