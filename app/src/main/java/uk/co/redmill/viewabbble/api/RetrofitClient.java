package uk.co.redmill.viewabbble.api;

/**
 * Created by dave @ RedMill Ltd on 5/22/17.
 */

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.redmill.viewabbble.utils.Injection;

public class RetrofitClient {

    private Retrofit retrofit;

    private RetrofitClient(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new DrTokenInterceptor())
                .build();
        retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(DribbbleService.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public DribbbleService getDRService(){
        return retrofit.create(DribbbleService.class);
    }

    public static RetrofitClient getInstance(){
        return ClientInstance.sInstance;
    }

    private static class ClientInstance{
        private static RetrofitClient sInstance=new RetrofitClient();
    }

    private class DrTokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            request=request.newBuilder().addHeader("Authorization",new StringBuilder()
                    .append("Bearer ")
                    .append(Injection.provideTokenValue())
                    .toString()).build();
            return chain.proceed(request);
        }
    }
}