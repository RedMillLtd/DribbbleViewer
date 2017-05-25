package uk.co.redmill.viewabbble.utils;

import android.content.SharedPreferences;

import uk.co.redmill.viewabbble.DribbbleApp;

/**
 * Created by dave @ RedMill Ltd on 5/23/17.
 */

public class SharedPrefManager {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String TOKEN_TYPE = "token_type";
    private static final String SCOPE = "scope";

    public static void putAccessToken(String token) {
        SharedPreferences.Editor editor = DribbbleApp.mSharedPrefs.edit();
        editor.putString(ACCESS_TOKEN, token);
        editor.commit();
    }

    public static void putTokenType(String tokenType) {
        SharedPreferences.Editor editor = DribbbleApp.mSharedPrefs.edit();
        editor.putString(TOKEN_TYPE, tokenType);
        editor.commit();
    }

    public static void putScope(String scope) {
        SharedPreferences.Editor editor = DribbbleApp.mSharedPrefs.edit();
        editor.putString(SCOPE, scope);
        editor.commit();
    }

    public static String getAccessToken() {
        return DribbbleApp.mSharedPrefs.getString(ACCESS_TOKEN, null);
    }
}
