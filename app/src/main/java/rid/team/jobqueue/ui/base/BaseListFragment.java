package rid.team.jobqueue.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import rid.team.jobqueue.ui.widget.NetworkProgressView;

/**
 * Created by bendik on 17.03.16.
 */
public abstract class BaseListFragment extends BaseFragment {
    protected NetworkProgressView mNetworkProgressView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNetworkProgressView = new NetworkProgressView(getContext());
        ((ViewGroup) view).addView(mNetworkProgressView);
    }
}
