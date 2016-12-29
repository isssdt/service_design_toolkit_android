package journey.api;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import journey.dto.JourneyListDTO;

/**
 * Created by longnguyen on 12/28/16.
 */

public class APIGetJourneyList extends AsyncTask<Void, Void, JourneyListDTO> {
    private HttpJSONGetJourneyListTaskCaller caller;
    private String url;

    public APIGetJourneyList(HttpJSONGetJourneyListTaskCaller caller, String url) {
        this.caller = caller;
        this.url = url;
    }

    @Override
    protected JourneyListDTO doInBackground(Void... voids) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate.getForObject(url, JourneyListDTO.class);
    }

    @Override
    protected void onPostExecute(JourneyListDTO journeyListDTO) {
        caller.onHttpGetTaskSucceeded(journeyListDTO);
    }
}
