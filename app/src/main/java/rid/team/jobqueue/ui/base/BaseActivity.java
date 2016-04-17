package rid.team.jobqueue.ui.base;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import rid.team.jobqueue.R;
import rid.team.jobqueue.di.base.BaseDaggerActivity;

/**
 * Created by bendik on 17.03.16.
 */
public abstract class BaseActivity extends BaseDaggerActivity {

    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    protected void changeFragment(Fragment fragment) {
        changeFragment(fragment, false);
    }

    protected void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction =
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }

    protected void initActionBar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setIcon(R.mipmap.ic_launcher);
            }
        }
    }
}
