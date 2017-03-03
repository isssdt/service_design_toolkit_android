package journey.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import common.api.APIGetJourneyListForRegister;
import common.constants.APIUrl;
import common.constants.ConstantValues;
import journey.dto.JourneyFieldResearcherDTO;
import journey.view.JourneyListView;
import poc.servicedesigntoolkit.getpost.R;

public class JourneyListActivity extends AppCompatActivity implements LocationListener {

    private static final String JOURNEYLIST_URL = APIUrl.API_GET_JOURNEY_LIST_FOR_REGISTER;
    private static final String REGISTER_URL = APIUrl.API_REGISTER_FIELD_RESEARCHER_WITH_JOURNEY;
    private LocationManager locationManager;
    private static final int share_location_request_code = 2;
    //ListView listView;
    String Username;


    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    Button signUp;
    String seljourney,JourneyName;
    String provider;
    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_recycle);

        Bundle extras = getIntent().getExtras();
        new APIGetJourneyListForRegister(((JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO)).getFieldResearcherDTO().getSdtUserDTO(), new JourneyListView(this)).execute();


        format = new SimpleDateFormat("dd MMM yyyy");
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);
    }



    @Override
    public void onLocationChanged(Location location) {
        double lat = (int) (location.getLatitude());
        double lng = (int) (location.getLongitude());
        Log.d("location","lat : "+lat + " lon : "+lng);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case share_location_request_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    registeruser(JourneyName);

                } else {
                    Toast.makeText(JourneyListActivity.this, "Permission denied to get your LOCATION", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}
