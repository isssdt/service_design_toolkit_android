package common.api;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import common.constants.APIUrl;
import common.view.AbstractView;

/**
 * Created by longnguyen on 3/2/17.
 */

public class APIFacade<T, X> extends AsyncTask<Void, Void, T> {
    private String url;
    private Class<T> outputClass;
    protected X input;
    private String method;
    private APIExecutor<T> apiExecutor;
    protected AbstractView view;

    public APIFacade(String url, Class<T> outputClass, X input, String method, AbstractView view) {
        this.url = url;
        this.outputClass = outputClass;
        this.input = input;
        this.method = method;
        this.view = view;
    }

    @Override
    protected void onPostExecute(T t) {
        apiExecutor.handleDataUponSuccess(t);
    }

    @Override
    protected T doInBackground(Void... voids) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        if (APIUrl.METHOD_GET.equals(method)) {
            return restTemplate.getForObject(url, outputClass);
        } else {
            return restTemplate.postForObject(url, input, outputClass);
        }
    }

    public void setApiExecutor(APIExecutor<T> apiExecutor) {
        this.apiExecutor = apiExecutor;
    }
}
