package rid.team.jobqueue.di.component;

import dagger.Component;
import rid.team.jobqueue.di.scope.FragmentScope;
import rid.team.jobqueue.ui.main.news.NewsFragment;
import rid.team.jobqueue.ui.main.news.NewsListFragment;

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent extends AppComponent {
    void inject(NewsListFragment fragment);
    void inject(NewsFragment newsFragment);
}
