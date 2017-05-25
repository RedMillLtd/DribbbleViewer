package uk.co.redmill.viewabbble.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import uk.co.redmill.viewabbble.api.models.Shots;

/**
 * Created by dave on 5/10/17.
 */

public interface DribbbleAuthService {

    @GET("https://dribbble.com/oauth/authorize")
    Observable<String> authorize(
            @Query("client_id") String clientId,
            @Query("redirect_uri") String redirectId,
            @Query("scope") String scope,
            @Query("state") String state
    );

    @POST("https://dribbble.com/oauth/token")
    Observable<String> token(
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("code") String code,
            @Query("redirect_uri") String state
    );
}
