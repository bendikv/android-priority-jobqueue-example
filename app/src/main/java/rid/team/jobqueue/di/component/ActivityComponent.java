package rid.team.jobqueue.di.component;

import dagger.Component;
import rid.team.jobqueue.di.scope.ActivityScope;
import rid.team.jobqueue.ui.main.MainActivity;
import rid.team.jobqueue.ui.main.news.DetailedNewsActivity;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent {
    void inject(MainActivity activity);
    void inject(DetailedNewsActivity activity);
}
