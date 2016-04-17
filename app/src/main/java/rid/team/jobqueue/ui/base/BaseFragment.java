package rid.team.jobqueue.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rid.team.jobqueue.di.base.BaseDaggerFragment;

/**
 * Created by bendik on 17.03.16.
 */
public abstract class BaseFragment extends BaseDaggerFragment {
    @Inject
    protected Bus mBus;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
