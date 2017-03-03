package journey.action;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;

import common.action.BaseAction;
import common.action.RecycleViewOnItemClick;
import common.api.APIRegisterFieldResearcherWithJourney;
import common.constants.ConstantValues;
import common.view.AbstractView;
import journey.aux.JourneyRecycleAdapter;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journeyemotion.emotionMeter;

/**
 * Created by longnguyen on 3/3/17.
 */

public class ACTION_BUTTON_RECYCLE_JOURNEY_LIST_VIEW_SIGN_UP extends BaseAction implements RecycleViewOnItemClick {
    public ACTION_BUTTON_RECYCLE_JOURNEY_LIST_VIEW_SIGN_UP(AbstractView abstractView, int position) {
        super(abstractView, position);
    }

    @Override
    public boolean validation(View view) {
        return false;
    }

    @Override
    public void onItemClick(View view) {
        JourneyRecycleAdapter journeyRecycleAdapter = (JourneyRecycleAdapter) ((RecyclerView) abstractView.getComponent(ConstantValues.COMPONENT_JOURNEY_LIST_VIEW_RECYCLE_VIEW))
                .getAdapter();
        final JourneyDTO journeyDTO = journeyRecycleAdapter.getJourneyDTOList().get(position);
        if (ConstantValues.COMPONENT_JOURNEY_LIST_VIEW_BUTTON_LABEL_VIEW.equals(((Button) view).getText())) {
            Intent i = new Intent(abstractView.getContext(), emotionMeter.class);
            i.putExtra("JourneyName", journeyDTO.getJourneyName());
            i.putExtra("Username", journeyDTO.getJourneyFieldResearcherListDTO().getJourneyFieldResearcherDTOList().get(0).getFieldResearcherDTO().getSdtUserDTO().getUsername());
            abstractView.getContext().startActivity(i);

        } else {
            final AlertDialog.Builder adb = new AlertDialog.Builder(abstractView.getContext());
            adb.setTitle("Register");
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            adb.setMessage("Journey Name : " + journeyDTO.getJourneyName() + "\n" +
                    "Date : " + format.format(journeyDTO.getStartDate()) + " - " + format.format(journeyDTO.getEndDate()));
            adb.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

//                    if (ContextCompat.checkSelfPermission(abstractView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(abstractView.getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, share_location_request_code);
//                    } else {
//                        Location location = locationManager.getLastKnownLocation(provider);
//                        if (location != null) {
//                            System.out.println("Provider " + provider + " has been selected.");
//                            onLocationChanged(location);
//                        } else {
//                            Toast.makeText(getApplicationContext(),"Location not available",Toast.LENGTH_SHORT );
//                        }
//                        registeruser(JourneyName);
//                    }
                    Bundle extras = abstractView.getContext().getIntent().getExtras();
                    JourneyFieldResearcherDTO journeyFieldResearcherDTO = (JourneyFieldResearcherDTO) extras.get(ConstantValues.BUNDLE_KEY_JOURNEY_FIELD_RESEARCHER_DTO);
                    journeyFieldResearcherDTO.setJourneyDTO(journeyDTO);
                    new APIRegisterFieldResearcherWithJourney(journeyFieldResearcherDTO, abstractView).execute();

                }
            });
            adb.setNegativeButton("Cancel", null);
            adb.show();
        }
    }
}
