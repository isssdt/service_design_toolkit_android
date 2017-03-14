package touchpoint.activity;

import android.Manifest;
import android.content.Intent;
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

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import common.constants.APIUrl;
import common.dto.RESTResponse;
import photo.SelectPhoto;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.view.TouchPointDetailsView;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan Pathak on 06-Oct-16.
 */

public class TouchPointDetailsActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

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
    String message = "", provider;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpoint_details_1);

        new TouchPointDetailsView(this);

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

    private void locationUpdate() {
        if (ContextCompat.checkSelfPermission(TouchPointDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TouchPointDetailsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, share_location_request_code);
        } else {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            } else {
                Toast.makeText(getApplicationContext(), "Location not available", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        new LocationUpdate().execute();
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

    private class LocationUpdate extends AsyncTask<Void, Void, RESTResponse> {

        @Override
        protected RESTResponse doInBackground(Void... params) {
            try {
                final String url = APIUrl.API_UDPDATE_FIELD_RESEARCHER_CURRENT_LOCATION;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                SdtUserDTO sdtUserDTO = new SdtUserDTO();
                sdtUserDTO.setUsername(username);

                FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
                fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
                fieldResearcherDTO.setCurrentLatitude(String.valueOf(lat));
                fieldResearcherDTO.setCurrentLongitude(String.valueOf(lng));

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
