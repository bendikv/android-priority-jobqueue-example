package rid.team.jobqueue.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import rid.team.jobqueue.api.ApiEndpoint;
import rid.team.jobqueue.api.service.NewsService;

/**
 * Created by bendik on 17.03.16.
 */
@Module(includes = NetworkModule.class)
public class ApiModule {

    @Provides @Singleton
    Endpoint provideEndpoint() {
        return new ApiEndpoint();
    }

    @Provides
    @Singleton
    NewsService provideContentService(RestAdapter restAdapter) {
        return restAdapter.create(NewsService.class);
    }

}
