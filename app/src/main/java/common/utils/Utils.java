package common.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import common.constants.APIUrl;

/**
 * Created by longnguyen on 3/2/17.
 */

public class Utils {
    public static void forwardToScreen(Activity activity, Class nextScreen, String key, Serializable object) {
        Intent intent = new Intent(activity, nextScreen);
        intent.putExtra(key, object);
        activity.startActivity(intent);
    }

    public static void uploadImageThread(final String photoPath, final String photoName) {
        Thread myUploadTask = new Thread(new Runnable() {
            public void run() {
                MultipartUtility multipart = null;
                try {
                    multipart = new MultipartUtility(APIUrl.API_PHOTO_UPLOAD, "UTF-8");
                    multipart.addFilePart("uploadedFile", new File(photoPath), photoName);
                    String response = multipart.finish(); // response from server.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        myUploadTask.start();
    }
}
