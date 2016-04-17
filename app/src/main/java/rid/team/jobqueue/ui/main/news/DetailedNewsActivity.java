package rid.team.jobqueue.ui.main.news;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import rid.team.jobqueue.R;
import rid.team.jobqueue.data.vo.News;
import rid.team.jobqueue.ui.base.BaseActivity;

/**
 * Created by bendik on 17.03.16.
 */
public class DetailedNewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActionBar();
        changeFragment(NewsFragment.newInstance((News) getIntent().getSerializableExtra(NewsFragment.EXTRA_NEWS)));
    }

    @Override
    protected void onStop() {
        super.onStop();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
