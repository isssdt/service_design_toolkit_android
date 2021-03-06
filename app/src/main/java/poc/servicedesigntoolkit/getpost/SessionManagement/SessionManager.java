/*
package poc.servicedesigntoolkit.getpost.SessionManagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

import poc.servicedesigntoolkit.getpost.MainActivity;

@SuppressLint("CommitPrefEdits")
public class SessionManager {
    // in future store phone number of the user
    public static final String KEY_JOURNEY = "poc/servicedesigntoolkit/getpost/journey";
    // Sharedpref file name
    private static final String PREF_NAME = "Userfile";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // Shared pref mode
    // User name (make variable public to access from outside)
    public static String KEY_NAME = "name";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    int PRIVATE_MODE = 0;

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    */
/**
     * Create login session
     *//*

    public void loginSession(String name) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name
        editor.putString(KEY_NAME, name);
        // Storing password

        editor.commit();
    }

    */
/**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     *//*

    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    */
/**
     * Get stored session data
     *//*

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        // user poc.servicedesigntoolkit.getpost.journey
        user.put(KEY_JOURNEY, pref.getString(KEY_JOURNEY, null));

        return user;
    }


    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setName(String name) {
        KEY_NAME = name;
    }
}
*/
