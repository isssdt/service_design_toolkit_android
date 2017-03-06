package common.api;

/**
 * Created by longnguyen on 3/2/17.
 */

public interface APIExecutor<T> {
    public void handleDataUponSuccess(T data);
}
