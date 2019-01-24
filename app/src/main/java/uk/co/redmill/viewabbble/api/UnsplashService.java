package uk.co.redmill.viewabbble.api;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import uk.co.redmill.viewabbble.api.models.DribbbleToken;

/**
 * Created by dave on 5/10/17.
 */

public interface UnsplashService {

    String HOST="https://api.unsplash.com/";

    @FormUrlEncoded
    @POST("https://unsplash.com/oauth/authorize")
    Single<DribbbleToken> getAuthorised(@Field("client_id") String client_id,
                                     @Field("response_type") String response_type,
                                     @Field("scope") String scope,
                                     @Field("redirect_uri") String redirect_uri);

    @FormUrlEncoded
    @POST("https://unsplash.com/oauth/token")
    Single<DribbbleToken> getDrToken(@Field("client_id") String client_id,
                                     @Field("client_secret") String client_secret,
                                     @Field("code") String code,
                                     @Field("redirect_uri") String redirect_uri);
//
//    @GET("shots")
//    Single<List<Shots>> getShotsList();
//
//    @GET("shots")
//    Single<List<Shots>> getShotsList(@QueryMap Map<String, String> map);
//
//    @GET("shots/{id}")
//    Single<Shots> getShots(@Path("id") int shotsId);
//
//    @GET("user")
//    Single<User> getAuthenticatedUser();
}
