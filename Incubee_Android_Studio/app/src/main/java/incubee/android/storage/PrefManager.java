package incubee.android.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by samuh on 10/18/2015.
 */
public class PrefManager {

    private static final String EMPTY_STRING ="";
    private static final String TAG = "PrefManager";

    enum Preference {
        /* General Preferences */
        USER_ID("incubee.android.username"),
        INCUBEE_ID("incubee.android.company_id")

        ;

        private String mPreferenceName;
        private boolean mIsUserBased;

        Preference(String pfName) {
            this(pfName, false);
        }

        Preference(String pfName, boolean bUserBased) {
            mPreferenceName = pfName;
            mIsUserBased = bUserBased;
        }

        public String getPreferenceName() { return mPreferenceName;	}
        public boolean isUserBasedPreference() { return mIsUserBased; }

    }

    /**
     * Sets the String preference value. Depending on the preference enum setting, the function will
     * also encrypt it if needed
     */
    private static void putStringValue(Context context, Preference preference, String value) {
        SharedPreferences prefs;
        if (preference.isUserBasedPreference()) {
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else {
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        final SharedPreferences.Editor spe = prefs.edit();
        spe.putString(getKey(context, preference), value);
        spe.apply();
    }

    /**
     * Sets the String preference value for the key provided. Use this for keys defined in xmls.
     * Ex: Setting preferences
     */
    private static void putStringValue(Context context, String key,boolean userBasedPreference, String value) {
        SharedPreferences prefs;
        if (userBasedPreference) {
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else {
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        final SharedPreferences.Editor spe = prefs.edit();
        spe.putString(key, value);
        spe.apply();
    }

    /**
     * Returns the String preference value. If not found then the default parameter passed will be returned.
     * Depending on the preference enum setting, the function will also decrypt the value before returning
     */
    static String getStringValue(Context context, Preference preference, String defaultValue) {
        SharedPreferences prefs;
        if(preference.isUserBasedPreference()){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return prefs.getString(getKey(context, preference), defaultValue);
    }

    /**
     * Returns the String preference value. If not found then the default parameter passed will be returned.
     * Use this method if keys are defined in xml Ex: Setting preferences
     */
    private static String getStringValue(Context context, String key, boolean userBasedPreference,String defaultValue) {
        SharedPreferences prefs;
        if(userBasedPreference){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return prefs.getString(key, defaultValue);
    }

    /**
     * Sets the boolean preference value
     */
    private static void putBooleanValue(Context context, Preference preference, boolean value) {
        SharedPreferences prefs;
        if(preference.isUserBasedPreference()){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        final SharedPreferences.Editor spe = prefs.edit();

        spe.putBoolean(getKey(context, preference), value);
        spe.apply();
    }

    /**
     * Sets the boolean preference value given key and preference. Use this method for cases where key is
     * defined in xml Ex: Setting preferences
     */
    private static void putBooleanValue(Context context, String key,boolean userBasedPreference, boolean value) {
        SharedPreferences prefs;
        if(userBasedPreference){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        final SharedPreferences.Editor spe = prefs.edit();
        spe.putBoolean(key, value);
        spe.apply();
    }


    /**
     * Returns the Boolean preference value. If not found then the default parameter passed will be returned
     */
    private static boolean getBooleanValue(Context context, Preference preference, boolean defaultValue) {
        SharedPreferences prefs;
        if(preference.isUserBasedPreference()){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return prefs.getBoolean(getKey(context, preference), defaultValue);
    }

    /**
     * Returns the Boolean preference value for the given key. Use this method when preference key is defined in xml
     * If not found then the default parameter passed will be returned
     */
    private static boolean getBooleanValue(Context context, String key, boolean userBasedPreference,boolean defaultValue) {
        SharedPreferences prefs;
        if(userBasedPreference){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return prefs.getBoolean(key, defaultValue);
    }

    /**
     * Sets the integer preference value
     */
    private static void putIntegerValue(Context context, Preference preference, int value) {
        SharedPreferences prefs;
        if(preference.isUserBasedPreference()){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        final SharedPreferences.Editor spe = prefs.edit();

        spe.putInt(getKey(context, preference), value);
        spe.apply();
    }

    /**
     * Returns the Integer preference value. If not found then the default parameter passed will be returned.
     * Use this method if keys are defined in xml Ex: Setting preferences
     */
    private static void putIntegerValue(Context context, String key,boolean userBasedPreference, int value) {
        SharedPreferences prefs;
        if(userBasedPreference){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        final SharedPreferences.Editor spe = prefs.edit();
        spe.putInt(key, value);
        spe.apply();
    }


    /**
     * Returns the Integer preference value. If not found then the default parameter passed will be returned
     */
    private static int getIntegerValue(Context context, Preference preference, int defaultValue) {
        SharedPreferences prefs;
        if(preference.isUserBasedPreference()){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return prefs.getInt(getKey(context, preference), defaultValue);
    }

    /**
     * Returns the Integer preference value. If not found then the default parameter passed will be returned.
     * Use this method if keys are defined in xml.
     */
    private static int getIntegerValue(Context context, String key, boolean userBasedPreference,int defaultValue) {
        SharedPreferences prefs;
        if(userBasedPreference){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return prefs.getInt(key, defaultValue);
    }


    /**
     * Returns the Long preference value. If not found then the default parameter passed will be returned
     */
    private static long getLongValue(Context context, Preference preference, long defaultValue) {
        SharedPreferences prefs;
        if(preference.isUserBasedPreference()){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return prefs.getLong(getKey(context, preference), defaultValue);
    }

    private static void putLongValue(Context context, Preference preference, long valueToSave){
        SharedPreferences prefs;
        if(preference.isUserBasedPreference()){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        final SharedPreferences.Editor spe = prefs.edit();

        spe.putLong(getKey(context, preference), valueToSave);
        spe.apply();
    }


    /**
     * Removes a preference value from the system
     * @param context
     * @param preference
     */
    private static void clearPreference(Context context, Preference preference) {
        SharedPreferences prefs;
        if(preference.isUserBasedPreference()){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        final SharedPreferences.Editor spe = prefs.edit();

        spe.remove(getKey(context, preference));
        spe.apply();
    }

    /**
     * Returns true if the passed preference is present in the set of preferences
     */
    private static boolean checkIfPresent(Context context, String key, boolean isUserBasedPreference) {
        SharedPreferences prefs;
        if(isUserBasedPreference){
            prefs = context.getSharedPreferences(getUserID(context),Context.MODE_PRIVATE);
        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return prefs.contains(key);
    }

    private static String getKey(Context context, Preference preference) {
        String pName = preference.getPreferenceName();
        if (preference.isUserBasedPreference()) {
            String uName = getStringValue(context, Preference.USER_ID, "");
            if (TextUtils.isEmpty(uName)) {
                Log.e(TAG, "Couldnt retrieve user name for pref: " + preference.name());
            }
        }
        return pName;
    }

    //++++++++++++++++++++++++++++

    /**
     * Sets the User ID preference. Note user Id is case sensitive
     */
    public static void setUserID(Context context, String userID) {
        putStringValue(context, Preference.USER_ID, userID);
    }

    /**
     * Returns the user id stored in preferences
     * Returns EMPTY STRING "" if not available
     */
    public static String getUserID(Context context){
        String userName = getStringValue(context, Preference.USER_ID, EMPTY_STRING);
        return userName;

    }


    /**
     * Sets the User ID preference. Note user Id is case sensitive
     */
    public static void setIncubeeID(Context context, String userID) {
        putStringValue(context, Preference.INCUBEE_ID, userID);
    }

    /**
     * Returns the user id stored in preferences
     * Returns EMPTY STRING "" if not available
     */
    public static String getIncubeeID(Context context){
        String userName = getStringValue(context, Preference.INCUBEE_ID, EMPTY_STRING);
        return userName;

    }


}
