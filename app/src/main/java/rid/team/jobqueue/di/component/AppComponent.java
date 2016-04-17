package rid.team.jobqueue.di.component;

import android.content.Context;

import com.path.android.jobqueue.JobManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import rid.team.jobqueue.api.service.NewsService;
import rid.team.jobqueue.data.models.NewsModel;
import rid.team.jobqueue.di.module.ApiModule;
import rid.team.jobqueue.di.module.AppModule;
import rid.team.jobqueue.di.module.DataModule;
import rid.team.jobqueue.jobqueue.news.BaseNewsJob;

/**
 * Created by bendik on 17.03.16.
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class, ApiModule.class})
public interface AppComponent {
    JobManager jobManager();
    Bus eventBus();
    Context appContext();

    OkHttpClient okHttpClient();

    NewsService newsService();
    NewsModel newsModel();

    void inject(BaseNewsJob baseNewsJob);
}
