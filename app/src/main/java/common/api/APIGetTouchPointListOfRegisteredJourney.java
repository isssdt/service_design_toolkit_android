package common.api;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import common.constants.APIUrl;
import common.constants.ConstantValues;
import common.view.AbstractView;

import geofence.Constants;
import geofence.GeofenceErrorMessages;
import geofence.GeofenceTransitionsIntentService;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.activity.TouchPointListActivity;
import touchpoint.aux_android.TouchPointFieldResearcherListAdapter;
import touchpoint.dto.TouchPointFieldResearcherDTO;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import touchpoint.view.TouchPointListView;
import user.dto.SdtUserDTO;

/**
 * Created by longnguyen on 3/4/17.
 */

public class APIGetTouchPointListOfRegisteredJourney extends APIFacade<TouchPointFieldResearcherListDTO, SdtUserDTO> implements APIExecutor<TouchPointFieldResearcherListDTO>,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>{//,LocationListener {


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

    public APIGetTouchPointListOfRegisteredJourney(SdtUserDTO input, AbstractView view) {
        super(APIUrl.API_GET_TOUCH_POINT_LIST_OF_REGISTERED_JOURNEY, TouchPointFieldResearcherListDTO.class, input, APIUrl.METHOD_POST, view);
        Log.d("TouchPointFieldResearcherListDTO : API", "TRY");
        setApiExecutor(this);
    }

    @Override
    public void handleDataUponSuccess(TouchPointFieldResearcherListDTO data) {
        Log.d("TouchPointFieldResearcherListDTO : API", "Success");
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

        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<Geofence>();

        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;

        // Retrieve an instance of the SharedPreferences object.
        mSharedPreferences =  view.getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);

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



        populateGeofenceList();

        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();

        mGoogleApiClient.connect();

        if(Constants.BAY_AREA_LANDMARKS1.isEmpty()){
            Log.d(TAG,"NoGeofence");
        }else{
            addGeofencesButtonHandler();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(view.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
*/

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
        //addGeofencesButtonHandler();
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
    }

    private GeofencingRequest getGeofencingRequest() {
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

    /**
     * Adds geofences, which sets alerts to be notified when the device enters or exits one of the
     * specified geofences. Handles the success or failure results returned by addGeofences().
     */
    public void addGeofencesButtonHandler() {
        Log.d(TAG,"addGeofencesButtonHandler");
        if (!mGoogleApiClient.isConnected()) {
            Log.i(TAG, "NO client");
            //Toast.makeText(view.getContext(),(R.string.not_connected), Toast.LENGTH_SHORT).show();
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

    /**
     * Removes geofences, which stops further notifications when the device enters or exits
     * previously registered geofences.
     */
    public void removeGeofencesButtonHandler() {
        //buildGoogleApiClient();
        //mGoogleApiClient.connect();
        //Log.d(TAG,"REMOVED");
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(view.getContext(),(R.string.not_connected), Toast.LENGTH_SHORT).show();
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

    /**
     * Runs when the result of calling addGeofences() and removeGeofences() becomes available.
     * Either method can complete successfully or with an error.
     *
     * Since this activity implements the {@link ResultCallback} interface, we are required to
     * define this method.
     *
     * @param status The Status returned through a PendingIntent when addGeofences() or
     *               removeGeofences() get called.
     */
    public void onResult(com.google.android.gms.common.api.Status status) {
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
            String errorMessage = GeofenceErrorMessages.getErrorString(view.getContext(),
                    status.getStatusCode());
            Log.e(TAG, errorMessage);
        }
    }

    /**
     * Gets a PendingIntent to send with the request to add or remove Geofences. Location Services
     * issues the Intent inside this PendingIntent whenever a geofence transition occurs for the
     * current list of geofences.
     *
     * @return A PendingIntent for the IntentService that handles geofence transitions.
     */
    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            Log.d(TAG,"getGeofencePendingIntent"+"null");
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(view.getContext(), GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        Log.d(TAG,"getGeofencePendingIntent");
        return PendingIntent.getService(view.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * This sample hard codes geofence data. A real app might dynamically create geofences based on
     * the user's location.
     */
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
                            entry.getValue().longitude, Float.parseFloat(entry.getKey().getTouchpointDTO().getRadius()))
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

    /**
     * Ensures that only one button is enabled at any time. The Add Geofences button is enabled
     * if the user hasn't yet added geofences. The Remove Geofences button is enabled if the
     * user has added geofences.
     */
}