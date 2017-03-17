package common.api;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.view.AbstractView;
import geofence.GeofenceTransitionsIntentService;
import touchpoint.aux_android.TouchPointFieldResearcherListAdapter;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class APIGetTouchPointListOfRegisteredJourney extends APIFacade<TouchPointFieldResearcherListDTO, SdtUserDTO> implements APIExecutor<TouchPointFieldResearcherListDTO>, LocationListener {


    PendingIntent mGeofencePendingIntent;
    public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 100;
    private List<Geofence> mGeofenceList;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = "Activity";
    LocationRequest mLocationRequest;
    double[] currentLatitudeGeo = {1.3051568, 1.3495207, 1.2922, 1.2951};
    double[] currentLongitudeGeo = {103.7739404, 103.7505944, 103.7766, 103.7738};

    double currentLatitude, currentLongitude;
    Boolean locationFound;
    // protected LocationManager locationManager;
    protected LocationListener locationListener;
    AbstractView v;

    public APIGetTouchPointListOfRegisteredJourney(SdtUserDTO input, AbstractView view) {
        super(APIUrl.API_GET_TOUCH_POINT_LIST_OF_REGISTERED_JOURNEY, TouchPointFieldResearcherListDTO.class, input, APIUrl.METHOD_POST, view);
        v = view;
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(TouchPointFieldResearcherListDTO data) {

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("Trial", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TouchPointFieldResearcherListDTO", json);
        editor.commit();

        Log.d("TouchPointFieldResearcherListDTO : API", json);

        ListView listView = (ListView) view.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_LIST_VIEW_TOUCH_POINT_LIST);
        listView.setAdapter(new TouchPointFieldResearcherListAdapter(view.getContext(), data.getTouchPointFieldResearcherDTOList()));
        TextView textView = (TextView) view.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_TEXT_VIEW_JOURNEY_NAME);
        textView.setText(data.getTouchPointFieldResearcherDTOList().get(0).getTouchpointDTO().getJourneyDTO().getJourneyName());
        Button button = (Button) view.getComponent(ConstantValues.COMPONENT_TOUCH_POINT_LIST_VIEW_BUTTON_SUBMIT_JOURNEY);
        button.setVisibility(View.VISIBLE);
        for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO : data.getTouchPointFieldResearcherDTOList()) {
            if (!ConstantValues.OTHERS_STATUS_DONE.equals(touchPointFieldResearcherDTO.getStatus())) {
                button.setVisibility(View.INVISIBLE);
                break;
            }
        }

        //if (savedInstanceState == null) {

        /*mGeofenceList = new ArrayList<Geofence>();

        int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(view.getContext());
        if (resp == ConnectionResult.SUCCESS) {

            initGoogleAPIClient();
            String geo = sharedPref.getString("TouchPointFieldResearcherListDTO", "");
            Log.d("TouchPointFieldResearcherListDTO", " EMPTY " + geo);
            TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = gson.fromJson(geo, TouchPointFieldResearcherListDTO.class);

            for (TouchPointFieldResearcherDTO touchPointDTO : touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList()) {
                if (!"NONE".equals(touchPointDTO.getTouchpointDTO().getLatitude()) && !"NONE".equals(touchPointDTO.getTouchpointDTO().getLongitude()) &&
                        !"NONE".equals(touchPointDTO.getTouchpointDTO().getRadius())) {
                    Log.d("geoFence", touchPointDTO.getTouchpointDTO().getLatitude());
                    createGeofences(Double.parseDouble(touchPointDTO.getTouchpointDTO().getLatitude()), Double.parseDouble(touchPointDTO.getTouchpointDTO().getLongitude()));
                }
            }
        } else {
            Log.e(TAG, "Your Device doesn't support Google Play Services.");
        }

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        //}*/
    }

    public void initGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(view.getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionAddListener)
                .addOnConnectionFailedListener(connectionFailedListener)
                .build();
        mGoogleApiClient.connect();
    }

    private GoogleApiClient.ConnectionCallbacks connectionAddListener =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    Log.i(TAG, "onConnected");


                    //   if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    {
                        Log.i("geofence", "debu");
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation

                        // for ActivityCompat#requestPermissions for more details.
                        if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                        if (location == null) {
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,APIGetTouchPointListOfRegisteredJourney.this);

                        } else {
                            //If everything went fine lets get latitude and longitude
                            currentLatitude = location.getLatitude();
                            currentLongitude = location.getLongitude();

                            Log.i(TAG, currentLatitude + " WORKS " + currentLongitude);

                            //createGeofences(currentLatitude, currentLongitude);
                            //registerGeofences(mGeofenceList);
                        }
                        }

                        try{
                            LocationServices.GeofencingApi.addGeofences(
                                    mGoogleApiClient,
                                    getGeofencingRequest(),
                                    getGeofencePendingIntent()
                            ).setResultCallback(new ResultCallback<com.google.android.gms.common.api.Status>() {

                                @Override
                                public void onResult(com.google.android.gms.common.api.Status status) {
                                    if (status.isSuccess()) {
                                        Log.i(TAG, "Saving Geofence");

                                    } else {
                                        Log.e(TAG, "Registering geofence failed: " + status.getStatusMessage() +
                                                " : " + status.getStatusCode());
                                    }
                                }
                            });

                        } catch (SecurityException securityException) {
                            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
                            Log.e(TAG, "Error");
                        }
                        return;
                    }

                }

                @Override
                public void onConnectionSuspended(int i) {

                    Log.e(TAG, "onConnectionSuspended");

                }
            };

    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Log.e(TAG, "onConnectionFailed");
                }
            };

    /**
     * Create a Geofence list
     */
    public void createGeofences(double latitude, double longitude) {
        String id = UUID.randomUUID().toString();
        Geofence fence = new Geofence.Builder()
                .setRequestId(id)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .setCircularRegion(latitude, longitude, 100)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)

                .build();
        mGeofenceList.add(fence);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(view.getContext(), GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(view.getContext(), 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        Log.i(TAG, "onLocationChanged");
    }

}