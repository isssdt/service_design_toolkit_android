package common.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import poc.servicedesigntoolkit.getpost.MultipartUtility;

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
                String charset = "UTF-8";
                String requestURL = "http://54.169.243.190:8080/service_design_toolkit-web/api/photo_upload";

                MultipartUtility multipart = null;
                try {
                    multipart = new MultipartUtility(requestURL, charset);
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
