package touchpoint.activity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.action.ActionFactoryProducer;
import common.action.ActionOnBackClick;
import common.api.APIGetTouchPointListOfRegisteredJourney;
import geofence.Constants;
import geofence.GeofenceErrorMessages;
import geofence.GeofenceTransitionsIntentService;
import journey.dto.JourneyFieldResearcherDTO;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import touchpoint.view.TouchPointListView;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

/**
 * Created by Gunjan Pathak on 11/01/2017.
 */

public class TouchPointListActivity extends AppCompatActivity  {/*implements
        ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<Status>{
*/
    protected static final String TAG = "TESTING";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * The list of geofences used in this sample.
     */
    protected ArrayList<Geofence> mGeofenceList;

    /**
     * Used to keep track of whether geofences were added.
     */
    private boolean mGeofencesAdded;

    /**
     * Used when requesting to add or remove geofences.
     */
    private PendingIntent mGeofencePendingIntent;

    /**
     * Used to persist application state about whether geofences were added.
     */

    public HashMap<TouchPointFieldResearcherDTO, LatLng> hashMap ;
    private SharedPreferences mSharedPreferences;
    private TouchPointListView touchPointListView;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visulaization_journey);


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Trial", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("JourneyFieldResearcherDTO", "");
        Log.d("TouchPointListActivity", " EMPTY "+json);
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = gson.fromJson(json, JourneyFieldResearcherDTO.class);
        touchPointListView = new TouchPointListView(this);

        new APIGetTouchPointListOfRegisteredJourney(journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO(), touchPointListView).execute();


/*
        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<Geofence>();

        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;

        // Retrieve an instance of the SharedPreferences object.
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE);

        // Get the value of mGeofencesAdded from SharedPreferences. Set to false as a default.
        mGeofencesAdded = mSharedPreferences.getBoolean(Constants.GEOFENCES_ADDED_KEY, false);
        //setButtonsEnabledState();
        hashMap = new HashMap<TouchPointFieldResearcherDTO, LatLng>();

        String geo = sharedPref.getString("TouchPointFieldResearcherListDTO", "");
        Log.d("TouchPointFieldResearcherListDTO", " EMPTY " + geo);
        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = gson.fromJson(geo, TouchPointFieldResearcherListDTO.class);

        for (TouchPointFieldResearcherDTO touchPointDTO:touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList()) {
            if(!touchPointDTO.getTouchpointDTO().getLatitude().toString().equals("NONE" )) {
                Log.d(TAG + "---------LATLANG---------", touchPointDTO.getTouchpointDTO().getLatitude() +":"+touchPointDTO.getTouchpointDTO().getLongitude());
                Log.d(TAG, touchPointDTO.getTouchpointDTO().getRadius());

                Constants.BAY_AREA_LANDMARKS1.put(touchPointDTO,new LatLng(Double.parseDouble(touchPointDTO.getTouchpointDTO().getLatitude()), Double.parseDouble(touchPointDTO.getTouchpointDTO().getLongitude())));
 //                   hashMap.put(touchPointDTO,new LatLng(Double.parseDouble(touchPointDTO.getTouchpointDTO().getLatitude()), Double.parseDouble(touchPointDTO.getTouchpointDTO().getLongitude())));
                //    Log.d("HASHMAPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP", hashMap.toString());
            }
        }

        // Get the geofences used. Geofence data is hard coded in this sample.
        populateGeofenceList();

        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();

        mGoogleApiClient.connect();
*/



    }

    @Override
    public void onBackPressed() {
        ActionFactoryProducer.getFactory(ActionOnBackClick.class.toString()).initOnBackClickAction(touchPointListView).onBackClick();
    }
/*    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    *//**
     * Runs when a GoogleApiClient object successfully connects.
     *//*
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
        addGeofencesButtonHandler();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason.
        Log.i(TAG, "Connection suspended");

        // onConnected() will be called again automatically when the service reconnects
    }*/

    /**
     * Builds and returns a GeofencingRequest. Specifies the list of geofences to be monitored.
     * Also specifies how the geofence notifications are initially triggered.
     */
    /*private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        Log.d("TAG","getGeofencingRequest");

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    *//**
     * Adds geofences, which sets alerts to be notified when the device enters or exits one of the
     * specified geofences. Handles the success or failure results returned by addGeofences().
     *//*
    public void addGeofencesButtonHandler() {
        Log.d(TAG,"addGeofencesButtonHandler");
        if (!mGoogleApiClient.isConnected()) {
            Log.i(TAG, "NO client");
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }
    }

    *//**
     * Removes geofences, which stops further notifications when the device enters or exits
     * previously registered geofences.
     *//*
    public void removeGeofencesButtonHandler() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // Remove geofences.
            LocationServices.GeofencingApi.removeGeofences(
                    mGoogleApiClient,
                    // This is the same pending intent that was used in addGeofences().
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }
    }

    private void logSecurityException(SecurityException securityException) {
        Log.e(TAG, "Invalid location permission. " +
                "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
    }

    *//**
     * Runs when the result of calling addGeofences() and removeGeofences() becomes available.
     * Either method can complete successfully or with an error.
     *
     * Since this activity implements the {@link ResultCallback} interface, we are required to
     * define this method.
     *
     * @param status The Status returned through a PendingIntent when addGeofences() or
     *               removeGeofences() get called.
     *//*
    public void onResult(Status status) {
        if (status.isSuccess()) {
Log.d(TAG,"onResult"+status);
            // Update state and save in shared preferences.
            mGeofencesAdded = !mGeofencesAdded;
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(Constants.GEOFENCES_ADDED_KEY, mGeofencesAdded);
            editor.apply();

            // Update the UI. Adding geofences enables the Remove Geofences button, and removing
            // geofences enables the Add Geofences button.
            //setButtonsEnabledState();
//
//            Toast.makeText(
//                    this,
//                    getString(mGeofencesAdded ? R.string.geofences_added :
//                            R.string.geofences_removed),
//                    Toast.LENGTH_SHORT
//            ).show();
        } else {
            Log.d(TAG,"onResult"+status);
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    status.getStatusCode());
            Log.e(TAG, errorMessage);
        }
    }

    *//**
     * Gets a PendingIntent to send with the request to add or remove Geofences. Location Services
     * issues the Intent inside this PendingIntent whenever a geofence transition occurs for the
     * current list of geofences.
     *
     * @return A PendingIntent for the IntentService that handles geofence transitions.
     *//*
    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            Log.d(TAG,"getGeofencePendingIntent"+"null");
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        Log.d(TAG,"getGeofencePendingIntent");
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    *//**
     * This sample hard codes geofence data. A real app might dynamically create geofences based on
     * the user's location.
     *//*
    public void populateGeofenceList() {
        Log.d(TAG,"populateGeofenceList");
        for (Map.Entry<TouchPointFieldResearcherDTO, LatLng> entry : Constants.BAY_AREA_LANDMARKS1.entrySet()) {
            Log.d(TAG,entry.getKey().getTouchpointDTO().getTouchPointDesc());
            Log.d(TAG, String.valueOf(entry.getValue().latitude));
            Log.d(TAG, String.valueOf(entry.getValue().longitude));


        }

            for (Map.Entry<TouchPointFieldResearcherDTO, LatLng> entry : Constants.BAY_AREA_LANDMARKS1.entrySet()) {
                Gson gson = new Gson();
                String json = gson.toJson(entry.getKey());
            mGeofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this

                    .setRequestId(entry.getKey().getTouchpointDTO().getTouchPointDesc())
                   // .setRequestId(json)

                    // Set the circular region of this geofence.
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,1000)
                           // Float.parseFloat(entry.getKey().getTouchpointDTO().getRadius())

                    // Set the expiration duration of the geofence. This geofence gets automatically
                    // removed after this period of time.
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)

                    // Set the transition types of interest. Alerts are only generated for these
                    // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)

                    // Create the geofence.
                    .build());
        }
//        for (Map.Entry<String, LatLng> entry : Constants.BAY_AREA_LANDMARKS.entrySet()) {
//            mGeofenceList.add(new Geofence.Builder()
//                    // Set the request ID of the geofence. This is a string to identify this
//                    // geofence.
//                    .setRequestId(entry.getKey())
//
//                    // Set the circular region of this geofence.
//                    .setCircularRegion(
//                            entry.getValue().latitude,
//                            entry.getValue().longitude, 100)
//                    // Float.parseFloat(entry.getKey().getTouchpointDTO().getRadius())
//
//                    // Set the expiration duration of the geofence. This geofence gets automatically
//                    // removed after this period of time.
//                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
//
//                    // Set the transition types of interest. Alerts are only generated for these
//                    // transition. We track entry and exit transitions in this sample.
//                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
//                            Geofence.GEOFENCE_TRANSITION_EXIT)
//
//                    // Create the geofence.
//                    .build());
//        }

    }

    *//**
     * Ensures that only one button is enabled at any time. The Add Geofences button is enabled
     * if the user hasn't yet added geofences. The Remove Geofences button is enabled if the
     * user has added geofences.
     */
    private void setButtonsEnabledState() {
        Log.d("Trial","trial");
    }

}