package poc.servicedesigntoolkit.getpost.journey.view;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import user.dto.SdtUserDTO;

/**
 * Created by Gunjan Pathak on 12/12/16.
 */

public class JourneyAPI extends AsyncTask<Void, Void, JourneyListDTO> {

    public static final String JOURNEY_URL = "http://54.169.243.190:8080/service_design_toolkit-web/api/get_journey_list_for_register";
    SdtUserDTO sdtUserDTO;
    List<Journey_model> journeyList;
    Journey_recycle journey_recycle;
    List<JourneyDTO> journeyDTOs;
    JourneyListDTO journeyListDTO;
    String username;

    @Override
    protected JourneyListDTO doInBackground(Void... params) {
        try {
            Log.d("check flow","-->3");
            journeyList = new ArrayList<Journey_model>();
            journeyDTOs = new ArrayList<JourneyDTO>();
            journeyListDTO = new JourneyListDTO();

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            JourneyListDTO journeyListDTO = restTemplate.getForObject(JOURNEY_URL, JourneyListDTO.class);

            for (JourneyDTO journeyDTO : journeyListDTO.getJourneyDTOList()) {

                Journey_model journey_model = new Journey_model(journeyDTO.getJourneyName(),
                        journeyDTO.getStartDate(),journeyDTO.getEndDate());

                journeyList.add(journey_model);

            }

            journey_recycle = new Journey_recycle();
            journey_recycle.setJourneyList(journeyList);
            Log.d("journeylisst",journeyList.toString());

            return journeyListDTO;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(JourneyListDTO journeyListDTO){

        this.journeyListDTO = journeyListDTO;

        journeyListDTO.setJourneyDTOList(journeyListDTO.getJourneyDTOList());
        journey_recycle = new Journey_recycle();
        journey_recycle.setJourneyList(journeyList);
        Log.d("journeylisst",journeyList.toString());
    }

    public JourneyDTO APIGetJourneyByName(JourneyDTO journeyDTO) {
        return journeyDTO;
    }

}
