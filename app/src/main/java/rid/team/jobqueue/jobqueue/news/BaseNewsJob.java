package rid.team.jobqueue.jobqueue.news;

import com.path.android.jobqueue.Params;

import javax.inject.Inject;

import rid.team.jobqueue.api.service.NewsService;
import rid.team.jobqueue.data.models.NewsModel;
import rid.team.jobqueue.di.component.AppComponent;
import rid.team.jobqueue.jobqueue.BaseJob;
import rid.team.jobqueue.jobqueue.Groups;

/**
 * Created by bendik on 17.03.16.
 */
public abstract class BaseNewsJob extends BaseJob {
    @Inject protected transient NewsService mNewsService;
    @Inject protected transient NewsModel mNewsModel;
    public BaseNewsJob() {
        super(new Params(BACKGROUND).groupBy(Groups.MAIN_CONTENT));
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }
}
