package uk.co.redmill.viewabbble;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.redmill.viewabbble.api.DribbbleAuthService;
import uk.co.redmill.viewabbble.api.DribbbleConfig;
import uk.co.redmill.viewabbble.api.DribbbleService;

/**
 * Created by dave on 5/18/17.
 */

public class DribbbleApp extends Application {

    private static final long TIME_OUT_MILLIS = 3000;
    private static final String BASE_PREFS = "base_preferences";
    private DribbbleConfig mApiConfiguration = null;
    private Retrofit mRetrofit = null;
    private Retrofit mAuthRetrofit = null;
    private DribbbleService mService;
    private DribbbleAuthService mAuthService;
    public static SharedPreferences mSharedPrefs;

    public static DribbbleApp getmContext() {
        return mContext;
    }

    private static DribbbleApp mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public DribbbleApp() {
    }

    public DribbbleApp(DribbbleConfig mApiConfiguration, Retrofit mRetrofit, Retrofit mAuthRetrofit) {
        this.mApiConfiguration = mApiConfiguration;
        this.mRetrofit = mRetrofit;
        this.mAuthRetrofit = mAuthRetrofit;
    }

    public DribbbleApp(DribbbleConfig apiConfiguration) {
        mApiConfiguration = apiConfiguration;
        mRetrofit = getRetrofitBuilder().build();
        mAuthRetrofit = getAuthRetrofitBuilder().build();
    }

    public DribbbleService getService() {
        if (mService == null) {
            mService = mRetrofit.create(DribbbleService.class);
        }
        return mService;
    }

    public DribbbleService getAuthService() {
        if (mService == null) {
            mAuthService = mRetrofit.create(DribbbleAuthService.class);
        }
        return mService;
    }

    protected OkHttpClient.Builder getHttpClientBuilder() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder
                .readTimeout(TIME_OUT_MILLIS, TimeUnit.MILLISECONDS)
                .connectTimeout(TIME_OUT_MILLIS, TimeUnit.MILLISECONDS);

        return httpClientBuilder;
    }

    protected Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mApiConfiguration.getBaseUrl())
                .client(getHttpClientBuilder().build());
    }

    protected Retrofit.Builder getAuthRetrofitBuilder() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mApiConfiguration.getAuthUrl())
                .client(getHttpClientBuilder().build());
    }
}
