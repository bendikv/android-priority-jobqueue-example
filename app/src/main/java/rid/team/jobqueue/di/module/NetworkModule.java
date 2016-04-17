package rid.team.jobqueue.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import rid.team.jobqueue.BuildConfig;
import rid.team.jobqueue.api.RetrofitErrorHandler;

/**
 * Created by bendik on 17.03.16.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    Gson provideGson() {
        Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
        return gson;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3, TimeUnit.SECONDS);
        client.setReadTimeout(4, TimeUnit.SECONDS);
        client.setWriteTimeout(4, TimeUnit.SECONDS);
        return client;
    }

    @Provides
    @Singleton
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(ErrorHandler errorHandler, Gson gson, Endpoint endpoint, Client client) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setConverter(new GsonConverter(gson))
                .setErrorHandler(errorHandler)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setEndpoint(endpoint)
                .build();
    }


    @Provides
    ErrorHandler provideErrorHandler(){
        return new RetrofitErrorHandler();
    }

}
