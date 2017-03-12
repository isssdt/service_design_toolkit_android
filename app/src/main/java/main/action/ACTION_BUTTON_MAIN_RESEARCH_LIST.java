package main.action;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import common.action.BaseAction;
import common.api.APIFieldResearcherRegister;
import common.constants.ConstantValues;
import common.view.AbstractView;
import main.view.MainView;
import poc.servicedesigntoolkit.getpost.MainActivity;
import user.dto.SdtUserDTO;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ACTION_BUTTON_MAIN_RESEARCH_LIST extends BaseAction implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 200;

    View viewCheck;
    SdtUserDTO sdtUserDTO;
    public ACTION_BUTTON_MAIN_RESEARCH_LIST(AbstractView abstractView) {
        super(abstractView);
    }

    @Override
    public void onClick(View view) {
        viewCheck=view;
        Context context = abstractView.getContext();
        SharedPreferences sharedPref = context.getSharedPreferences("Trial", Context.MODE_PRIVATE);

        if (!validation(view)) {
            return;
        }
        MainView mainView = (MainView) abstractView;
        sdtUserDTO = new SdtUserDTO();
        String Username = ((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString();
        sdtUserDTO.setUsername(((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Username",Username);
        editor.commit();
        if (!checkPermission()) {

            requestPermission();

        } else {
            //Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
            new APIFieldResearcherRegister(sdtUserDTO, abstractView).execute();
        }


        //new APIFieldResearcherRegister(sdtUserDTO, abstractView).execute();
    }

    @Override
    public boolean validation(View view) {
        MainView mainView = (MainView) abstractView;
        if (TextUtils.isEmpty(((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).getText().toString())){
            ((EditText) mainView.getComponent(ConstantValues.COMPONENT_MAIN_VIEW_EDIT_TEXT_USERNAME)).setError(ConstantValues.ALERT_MESSAGE_NO_USERNAME_ENTERED);
            return false;
        }
        return true;
    }

    private boolean checkPermission() {
        int location = ContextCompat.checkSelfPermission(abstractView.getContext(), ACCESS_FINE_LOCATION);
        int camera = ContextCompat.checkSelfPermission(abstractView.getContext(), CAMERA);
        int storage = ContextCompat.checkSelfPermission(abstractView.getContext(), WRITE_EXTERNAL_STORAGE);

        return location == PackageManager.PERMISSION_GRANTED && camera == PackageManager.PERMISSION_GRANTED && storage == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions((Activity) viewCheck.getRootView().getContext(),new String[]{ACCESS_FINE_LOCATION, CAMERA,WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted && storageAccepted) {
                        new APIFieldResearcherRegister(sdtUserDTO, abstractView).execute();
                    }
                    //Snackbar.make(viewCheck, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        Snackbar.make(viewCheck, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA,WRITE_EXTERNAL_STORAGE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        Log.d("ERROR", "ERROR");
    }
}