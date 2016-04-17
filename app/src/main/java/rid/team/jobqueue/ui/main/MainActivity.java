package rid.team.jobqueue.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import rid.team.jobqueue.R;
import rid.team.jobqueue.ui.base.BaseActivity;
import rid.team.jobqueue.ui.main.news.NewsListFragment;

/**
 * Created by bendik on 17.03.16.
 */
public class MainActivity extends BaseActivity {


    public static void startActivity(android.app.Activity activity) {
        startActivity(activity, false);
    }

    public static void startActivity(android.app.Activity activity, boolean clearTop) {
        Intent intent = new Intent(activity, MainActivity.class);

        if (clearTop) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActionBar();
        changeFragment(NewsListFragment.newInstance());
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
