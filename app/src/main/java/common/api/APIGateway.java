package common.api;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import common.constants.APIUrl;

/**
 * Created by longnguyen on 12/28/16.
 */

public class APIGateway extends AsyncTask<Void, Void, Object> {
    private APICaller caller;
    private String url;
    private Class outputClass;
    private Object input;
    private String method;

    public APIGateway(APICaller caller, String url, Class outputClass, Object input, String method) {
        this.caller = caller;
        this.url = url;
        this.outputClass = outputClass;
        this.input = input;
        this.method = method;
    }

    public APIGateway(APICaller caller) {
        this.caller = caller;
    }

    @Override
    protected Object doInBackground(Void... voids) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        if (APIUrl.METHOD_GET.equals(method)) {
            return restTemplate.getForObject(url, outputClass);
        }
        else {
            return restTemplate.postForObject(url, input, outputClass);
        }
    }

    @Override
    protected void onPostExecute(Object outputData) {
        caller.onAPICallSucceeded(outputData);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOutputClass(Class outputClass) {
        this.outputClass = outputClass;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
