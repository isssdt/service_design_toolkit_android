package common.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;

/**
 * Created by longnguyen on 3/2/17.
 */

public class Utils {
    public static void forwardToScreen(Activity activity, Class nextScreen, String key, Serializable object) {
        Intent intent = new Intent(activity, nextScreen);
        intent.putExtra(key, object);
        activity.startActivity(intent);
    }
}
