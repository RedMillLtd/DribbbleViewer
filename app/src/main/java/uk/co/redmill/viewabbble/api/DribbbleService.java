package uk.co.redmill.viewabbble.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import uk.co.redmill.viewabbble.api.models.DribbbleToken;
import uk.co.redmill.viewabbble.api.models.Shots;
import uk.co.redmill.viewabbble.api.models.User;

/**
 * Created by dave on 5/10/17.
 */

public interface DribbbleService {

    String HOST="https://api.dribbble.com/v1/";

    @FormUrlEncoded
    @POST("https://dribbble.com/oauth/token")
    Single<DribbbleToken> getDrToken(@Field("client_id")String client_id,
                                     @Field("client_secret")String client_secret,
                                     @Field("code")String code,
                                     @Field("redirect_uri")String redirect_uri);

    @GET("shots")
    Single<List<Shots>> getShotsList();

    @GET("shots")
    Single<List<Shots>> getShotsList(@QueryMap Map<String,String> map);

    @GET("shots/{id}")
    Single<Shots> getShots(@Path("id")int shotsId);

    @GET("user")
    Single<User> getAuthenticatedUser();
}
