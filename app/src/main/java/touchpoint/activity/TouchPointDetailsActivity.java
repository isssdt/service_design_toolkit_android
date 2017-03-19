package touchpoint.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import common.constants.APIUrl;
import common.dto.RESTResponse;
import journey.dto.JourneyFieldResearcherDTO;
import photo.SelectPhoto;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.view.TouchPointDetailsView;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan Pathak on 06-Oct-16.
 */

public class TouchPointDetailsActivity extends AppCompatActivity implements View.OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {

    private static final String completed_confirmation = "Journey has been marked as Completed";
    private static final int take_photo_request_code = 1;
    private static final int share_location_request_code = 2;
    EditText touchpointName_edit, imagepathEdit, channelDescription_edit, channel_edit, action_edit, comment_edit, reaction_edit, expected_edit, actual_edit;
    RatingBar ratingBar;
    TextView image;
    Button submit, reset;
    Spinner time;
    ImageButton photo;
    String touchpoint, username, actual_time, actual_unit, JourneyName, reaction_string, comment_string;
    String name, action, channel_desc, channel, expected_unit;
    double lat, lng;
    Integer expected_time;
    Integer id;
    List<String> time_data;
    String id_String, rating_string;
    String rating_intent, reaction_intent, comment_intent, actual_string, actual_time_unit, imagepath;
    int rating;
    private LocationManager locationManager;
    private GoogleApiClient mGoogleApiClient;
    String message = "", provider;
    double currentLatitude, currentLongitude;
    protected Location mLastLocation;
    private final String TAG = "TouchPointDetailsActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpoint_details_1);
        new TouchPointDetailsView(this);
        buildGoogleApiClient();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Trial", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        username = sharedPref.getString("Username", "");
        Log.d("Username",username);
        //JourneyFieldResearcherDTO journeyFieldResearcherDTO = gson.fromJson(journey, JourneyFieldResearcherDTO.class);
        //username =journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername();


    }

    @Override
    public void onClick(View v) {

        if (v == photo) {
            if (ContextCompat.checkSelfPermission(TouchPointDetailsActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TouchPointDetailsActivity.this, new String[]{Manifest.permission.CAMERA}, take_photo_request_code);
            } else {
                Intent i = new Intent(TouchPointDetailsActivity.this, SelectPhoto.class);
               /* i.putExtra("Touchpoint", touchpoint);
                i.putExtra("Username", username);
                i.putExtra("JourneyName", JourneyName);
                i.putExtra("Action", action);
                i.putExtra("Channel", channel);
                i.putExtra("Channel_Desc", channel_desc);
                i.putExtra("Id", id);
                i.putExtra("Expected_time", expected_time);
                i.putExtra("Expected_unit", expected_unit);

                if (null != String.valueOf(ratingBar.getRating())) {
                    i.putExtra("rating", rating_string);
                    i.putExtra("comment", comment_string);
                    i.putExtra("reaction", reaction_string);
                    i.putExtra("Actual_time", actual_string);
                    i.putExtra("Actual_unit", actual_time_unit);
                }
*/
                startActivity(i);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case take_photo_request_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(TouchPointDetailsActivity.this, SelectPhoto.class);
                    /*i.putExtra("Touchpoint", touchpoint);
                    i.putExtra("Username", username);
                    i.putExtra("JourneyName", JourneyName);

                    i.putExtra("Action", action);
                    i.putExtra("Channel", channel);
                    i.putExtra("Channel_Desc", channel_desc);
                    i.putExtra("Id", id);
                    i.putExtra("Expected_time", expected_time);
                    i.putExtra("Expected_unit", expected_unit);

                    if (null != String.valueOf(ratingBar.getRating())) {
                        i.putExtra("rating", rating_string);
                        i.putExtra("comment", comment_string);
                        i.putExtra("reaction", reaction_string);
                        i.putExtra("Actual_time", actual_string);
                        i.putExtra("Actual_unit", actual_time_unit);
                    }
*/
                    startActivity(i);
                } else {
                    Toast.makeText(TouchPointDetailsActivity.this, "Permission denied to read your CAMERA", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case share_location_request_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(TouchPointDetailsActivity.this, "Permission denied to get your LOCATION", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }else{
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                currentLatitude = mLastLocation.getLatitude();
                currentLongitude = mLastLocation.getLongitude();
                Log.i(TAG, "Location "+"Lat : "+ mLastLocation.getLatitude()+" Lon "+ mLastLocation.getLongitude());
                new LocationUpdate().execute();

            } else {
                Log.i(TAG, "Location NULL");
                Toast.makeText(this, "Location NULL", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    private class LocationUpdate extends AsyncTask<Void, Void, RESTResponse> {

        @Override
        protected RESTResponse doInBackground(Void... params) {
            try {
                Log.d(TAG,"Location update");
                final String url = APIUrl.API_UDPDATE_FIELD_RESEARCHER_CURRENT_LOCATION;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(username);

                FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
                fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
                fieldResearcherDTO.setCurrentLatitude(String.valueOf(currentLatitude));
                fieldResearcherDTO.setCurrentLongitude(String.valueOf(currentLongitude));
                Gson gson = new Gson();
                String json = gson.toJson(fieldResearcherDTO);
                Log.d(TAG,"DATa"+ fieldResearcherDTO.getSdtUserDTO().getUsername());
                Log.d(TAG,"DATa"+ json);
                RESTResponse response =
                        restTemplate.postForObject(url, fieldResearcherDTO, RESTResponse.class);
                String locationmessage = response.getMessage();
                Log.d("Location : ", locationmessage);
                Log.d("Location : ", fieldResearcherDTO.toString());

            } catch (Exception e) {
                Log.d("Location ", "In Exception ");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(RESTResponse response) {

        }
    }

}
